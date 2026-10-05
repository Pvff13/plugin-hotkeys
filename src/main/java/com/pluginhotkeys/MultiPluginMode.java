package com.pluginhotkeys;

/** What a hotkey does when its slot lists more than one plugin. */
public enum MultiPluginMode
{
	SYNC("Sync: all on or all off"),
	FLIP("Flip: each one swaps");

	private final String displayName;

	MultiPluginMode(String displayName)
	{
		this.displayName = displayName;
	}

	@Override
	public String toString()
	{
		return displayName;
	}
}
