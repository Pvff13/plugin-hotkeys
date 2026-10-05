package com.pluginhotkeys;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.Keybind;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginInstantiationException;
import net.runelite.client.plugins.PluginManager;
import net.runelite.client.util.HotkeyListener;

@PluginDescriptor(
	name = "Plugin Hotkeys",
	description = "Bind a hotkey to any installed plugin to switch it on or off.",
	tags = {"hotkey", "toggle", "enable", "disable", "plugin", "keybind"}
)
public class PluginHotkeysPlugin extends Plugin
{
	private static final Logger log = LoggerFactory.getLogger(PluginHotkeysPlugin.class);

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Inject
	private ConfigManager configManager;

	@Inject
	private PluginManager pluginManager;

	@Inject
	private KeyManager keyManager;

	@Inject
	private PluginHotkeysConfig config;

	private final List<HotkeyListener> listeners = new ArrayList<>();

	@Provides
	PluginHotkeysConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PluginHotkeysConfig.class);
	}

	@Override
	protected void startUp()
	{
		for (int slot = 1; slot <= PluginHotkeysConfig.SLOTS; slot++)
		{
			final int s = slot;
			HotkeyListener listener = new HotkeyListener(() -> slotKeybind(s))
			{
				@Override
				public void hotkeyPressed()
				{
					toggleSlot(s);
				}
			};
			listeners.add(listener);
			keyManager.registerKeyListener(listener);
		}
	}

	@Override
	protected void shutDown()
	{
		for (HotkeyListener listener : listeners)
		{
			keyManager.unregisterKeyListener(listener);
		}
		listeners.clear();
	}

	private Keybind slotKeybind(int slot)
	{
		Keybind keybind = configManager.getConfiguration(PluginHotkeysConfig.GROUP, "key" + slot, Keybind.class);
		return keybind == null ? Keybind.NOT_SET : keybind;
	}

	private String slotPluginName(int slot)
	{
		return configManager.getConfiguration(PluginHotkeysConfig.GROUP, "plugin" + slot);
	}

	private void toggleSlot(int slot)
	{
		List<String> queries = ToggleLogic.splitQueries(slotPluginName(slot));
		if (queries.isEmpty())
		{
			return;
		}

		// Hotkeys arrive on the Swing event thread, which is where PluginManager insists plugins are
		// started and stopped from (the plugin list does the same).
		SwingUtilities.invokeLater(() -> toggle(queries));
	}

	private void toggle(List<String> queries)
	{
		List<Plugin> candidates = new ArrayList<>();
		List<String> names = new ArrayList<>();
		for (Plugin plugin : pluginManager.getPlugins())
		{
			PluginDescriptor descriptor = plugin.getClass().getAnnotation(PluginDescriptor.class);
			if (plugin == this || descriptor == null || descriptor.hidden())
			{
				continue;
			}
			candidates.add(plugin);
			names.add(plugin.getName());
		}

		// Work out every plugin the slot refers to first, remembering the names that didn't resolve.
		List<Plugin> targets = new ArrayList<>();
		List<String> notes = new ArrayList<>();
		for (String query : queries)
		{
			PluginNameMatcher.Result result = PluginNameMatcher.find(names, query);
			if (result.kind == PluginNameMatcher.Kind.NOT_FOUND)
			{
				notes.add("No installed plugin matches \"" + query + "\".");
			}
			else if (result.kind == PluginNameMatcher.Kind.AMBIGUOUS)
			{
				notes.add("\"" + query + "\" matches more than one plugin (" + String.join(", ", result.candidates) + "), type more of the name.");
			}
			else if (!targets.contains(candidates.get(result.index)))
			{
				targets.add(candidates.get(result.index));
			}
		}

		boolean[] active = new boolean[targets.size()];
		for (int i = 0; i < active.length; i++)
		{
			active[i] = pluginManager.isPluginActive(targets.get(i));
		}
		boolean[] desired = ToggleLogic.desiredStates(active, config.multiPluginMode());

		List<String> enabled = new ArrayList<>();
		List<String> disabled = new ArrayList<>();
		for (int i = 0; i < targets.size(); i++)
		{
			Plugin target = targets.get(i);
			if (desired[i] == active[i])
			{
				continue;
			}

			try
			{
				pluginManager.setPluginEnabled(target, desired[i]);
				if (desired[i])
				{
					pluginManager.startPlugin(target);
					enabled.add(target.getName());
				}
				else
				{
					pluginManager.stopPlugin(target);
					disabled.add(target.getName());
				}
			}
			catch (PluginInstantiationException ex)
			{
				log.warn("Couldn't {} {}", desired[i] ? "start" : "stop", target.getName(), ex);
				notes.add("Couldn't " + (desired[i] ? "start " : "stop ") + target.getName() + ", see the log for details.");
			}
		}

		if (!enabled.isEmpty())
		{
			notes.add(0, "Enabled " + String.join(", ", enabled) + ".");
		}
		if (!disabled.isEmpty())
		{
			notes.add(enabled.isEmpty() ? 0 : 1, "Disabled " + String.join(", ", disabled) + ".");
		}
		if (!notes.isEmpty())
		{
			say(String.join(" ", notes));
		}
	}

	private void say(String text)
	{
		if (!config.chatMessage())
		{
			return;
		}

		clientThread.invokeLater(() ->
		{
			if (client.getGameState() != GameState.LOGGED_IN)
			{
				return;
			}
			chatMessageManager.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage("Plugin Hotkeys: " + text)
				.build());
		});
	}
}
