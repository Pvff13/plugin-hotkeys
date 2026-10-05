package com.pluginhotkeys;

import java.util.ArrayList;
import java.util.List;

final class ToggleLogic
{
	/** Splits "a, b,,c" into the non-blank names a, b, c. */
	static List<String> splitQueries(String text)
	{
		List<String> queries = new ArrayList<>();
		if (text == null)
		{
			return queries;
		}
		for (String part : text.split(","))
		{
			if (!PluginNameMatcher.normalize(part).isEmpty())
			{
				queries.add(part.trim());
			}
		}
		return queries;
	}

	/**
	 * @param active whether each plugin is currently running, in the order they were listed
	 * @return whether each plugin should be running after the press. SYNC follows the first plugin, so the
	 * whole list goes on if it was off and off if it was on; FLIP swaps every plugin on its own.
	 */
	static boolean[] desiredStates(boolean[] active, MultiPluginMode mode)
	{
		boolean[] desired = new boolean[active.length];
		for (int i = 0; i < active.length; i++)
		{
			desired[i] = mode == MultiPluginMode.SYNC ? !active[0] : !active[i];
		}
		return desired;
	}

	private ToggleLogic()
	{
	}
}
