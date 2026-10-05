package com.pluginhotkeys;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;

@ConfigGroup(PluginHotkeysConfig.GROUP)
public interface PluginHotkeysConfig extends Config
{
	String GROUP = "pluginhotkeys";
	int SLOTS = 10;

	@ConfigItem(
		position = 0,
		keyName = "chatMessage",
		name = "Chat message",
		description = "Say in chat when a hotkey switches a plugin on or off, or can't find the plugin."
	)
	default boolean chatMessage()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "multiPluginMode",
		name = "Several plugins",
		description = "What a hotkey does when its slot lists more than one plugin. Sync: if the first plugin is on they all go off, otherwise they all go on. Flip: each plugin swaps its own state."
	)
	default MultiPluginMode multiPluginMode()
	{
		return MultiPluginMode.SYNC;
	}

	@ConfigItem(
		position = 2,
		keyName = "plugin1",
		name = "Slot 1 plugin",
		description = "Name of the plugin to toggle, as shown in the plugin list. Capitals and punctuation do not matter, and a unique part of the name is enough. Separate several plugins with commas."
	)
	default String plugin1()
	{
		return "";
	}

	@ConfigItem(
		position = 3,
		keyName = "key1",
		name = "Slot 1 hotkey",
		description = "Press this to switch the plugin on or off. Use a modifier such as Ctrl or Alt so it doesn't fire while you type."
	)
	default Keybind key1()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		position = 4,
		keyName = "plugin2",
		name = "Slot 2 plugin",
		description = "Name of the plugin to toggle, as shown in the plugin list. Capitals and punctuation do not matter, and a unique part of the name is enough. Separate several plugins with commas."
	)
	default String plugin2()
	{
		return "";
	}

	@ConfigItem(
		position = 5,
		keyName = "key2",
		name = "Slot 2 hotkey",
		description = "Press this to switch the plugin on or off. Use a modifier such as Ctrl or Alt so it doesn't fire while you type."
	)
	default Keybind key2()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		position = 6,
		keyName = "plugin3",
		name = "Slot 3 plugin",
		description = "Name of the plugin to toggle, as shown in the plugin list. Capitals and punctuation do not matter, and a unique part of the name is enough. Separate several plugins with commas."
	)
	default String plugin3()
	{
		return "";
	}

	@ConfigItem(
		position = 7,
		keyName = "key3",
		name = "Slot 3 hotkey",
		description = "Press this to switch the plugin on or off. Use a modifier such as Ctrl or Alt so it doesn't fire while you type."
	)
	default Keybind key3()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		position = 8,
		keyName = "plugin4",
		name = "Slot 4 plugin",
		description = "Name of the plugin to toggle, as shown in the plugin list. Capitals and punctuation do not matter, and a unique part of the name is enough. Separate several plugins with commas."
	)
	default String plugin4()
	{
		return "";
	}

	@ConfigItem(
		position = 9,
		keyName = "key4",
		name = "Slot 4 hotkey",
		description = "Press this to switch the plugin on or off. Use a modifier such as Ctrl or Alt so it doesn't fire while you type."
	)
	default Keybind key4()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		position = 10,
		keyName = "plugin5",
		name = "Slot 5 plugin",
		description = "Name of the plugin to toggle, as shown in the plugin list. Capitals and punctuation do not matter, and a unique part of the name is enough. Separate several plugins with commas."
	)
	default String plugin5()
	{
		return "";
	}

	@ConfigItem(
		position = 11,
		keyName = "key5",
		name = "Slot 5 hotkey",
		description = "Press this to switch the plugin on or off. Use a modifier such as Ctrl or Alt so it doesn't fire while you type."
	)
	default Keybind key5()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		position = 12,
		keyName = "plugin6",
		name = "Slot 6 plugin",
		description = "Name of the plugin to toggle, as shown in the plugin list. Capitals and punctuation do not matter, and a unique part of the name is enough. Separate several plugins with commas."
	)
	default String plugin6()
	{
		return "";
	}

	@ConfigItem(
		position = 13,
		keyName = "key6",
		name = "Slot 6 hotkey",
		description = "Press this to switch the plugin on or off. Use a modifier such as Ctrl or Alt so it doesn't fire while you type."
	)
	default Keybind key6()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		position = 14,
		keyName = "plugin7",
		name = "Slot 7 plugin",
		description = "Name of the plugin to toggle, as shown in the plugin list. Capitals and punctuation do not matter, and a unique part of the name is enough. Separate several plugins with commas."
	)
	default String plugin7()
	{
		return "";
	}

	@ConfigItem(
		position = 15,
		keyName = "key7",
		name = "Slot 7 hotkey",
		description = "Press this to switch the plugin on or off. Use a modifier such as Ctrl or Alt so it doesn't fire while you type."
	)
	default Keybind key7()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		position = 16,
		keyName = "plugin8",
		name = "Slot 8 plugin",
		description = "Name of the plugin to toggle, as shown in the plugin list. Capitals and punctuation do not matter, and a unique part of the name is enough. Separate several plugins with commas."
	)
	default String plugin8()
	{
		return "";
	}

	@ConfigItem(
		position = 17,
		keyName = "key8",
		name = "Slot 8 hotkey",
		description = "Press this to switch the plugin on or off. Use a modifier such as Ctrl or Alt so it doesn't fire while you type."
	)
	default Keybind key8()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		position = 18,
		keyName = "plugin9",
		name = "Slot 9 plugin",
		description = "Name of the plugin to toggle, as shown in the plugin list. Capitals and punctuation do not matter, and a unique part of the name is enough. Separate several plugins with commas."
	)
	default String plugin9()
	{
		return "";
	}

	@ConfigItem(
		position = 19,
		keyName = "key9",
		name = "Slot 9 hotkey",
		description = "Press this to switch the plugin on or off. Use a modifier such as Ctrl or Alt so it doesn't fire while you type."
	)
	default Keybind key9()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		position = 20,
		keyName = "plugin10",
		name = "Slot 10 plugin",
		description = "Name of the plugin to toggle, as shown in the plugin list. Capitals and punctuation do not matter, and a unique part of the name is enough. Separate several plugins with commas."
	)
	default String plugin10()
	{
		return "";
	}

	@ConfigItem(
		position = 21,
		keyName = "key10",
		name = "Slot 10 hotkey",
		description = "Press this to switch the plugin on or off. Use a modifier such as Ctrl or Alt so it doesn't fire while you type."
	)
	default Keybind key10()
	{
		return Keybind.NOT_SET;
	}
}
