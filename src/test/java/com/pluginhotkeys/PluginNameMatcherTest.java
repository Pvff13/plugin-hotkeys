package com.pluginhotkeys;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PluginNameMatcherTest
{
	private static final List<String> NAMES = Arrays.asList(
		"Ground Items", "Ground Markers", "[S] Npc Notifier", "NPC Indicators", "Swing Rainbow", "XP Tracker");

	@Test
	public void exactNameIgnoresCaseAndPunctuation()
	{
		PluginNameMatcher.Result r = PluginNameMatcher.find(NAMES, "swing-rainbow");

		assertEquals(PluginNameMatcher.Kind.FOUND, r.kind);
		assertEquals(4, r.index);
	}

	@Test
	public void uniqueStartIsEnough()
	{
		PluginNameMatcher.Result r = PluginNameMatcher.find(NAMES, "xp");

		assertEquals(PluginNameMatcher.Kind.FOUND, r.kind);
		assertEquals(5, r.index);
	}

	@Test
	public void uniqueContainsFindsNamesWithADecoratedPrefix()
	{
		PluginNameMatcher.Result r = PluginNameMatcher.find(NAMES, "npc notifier");

		assertEquals(PluginNameMatcher.Kind.FOUND, r.kind);
		assertEquals(2, r.index);
	}

	@Test
	public void twoPluginsInTheDecidingTierIsAmbiguous()
	{
		PluginNameMatcher.Result r = PluginNameMatcher.find(NAMES, "ground");

		assertEquals(PluginNameMatcher.Kind.AMBIGUOUS, r.kind);
		assertEquals(Arrays.asList("Ground Items", "Ground Markers"), r.candidates);
	}

	@Test
	public void anExactNameWinsEvenWhenLongerNamesStartWithIt()
	{
		List<String> names = Arrays.asList("Ground Items Plus", "Ground Items");

		PluginNameMatcher.Result r = PluginNameMatcher.find(names, "ground items");

		assertEquals(PluginNameMatcher.Kind.FOUND, r.kind);
		assertEquals(1, r.index);
	}

	@Test
	public void startsWithOutranksContains()
	{
		// "npc" starts "NPC Indicators" but is only contained in "[S] Npc Notifier".
		PluginNameMatcher.Result r = PluginNameMatcher.find(NAMES, "npc");

		assertEquals(PluginNameMatcher.Kind.FOUND, r.kind);
		assertEquals(3, r.index);
	}

	@Test
	public void blankAndUnknownNamesAreNotFound()
	{
		assertEquals(PluginNameMatcher.Kind.NOT_FOUND, PluginNameMatcher.find(NAMES, "  ").kind);
		assertEquals(PluginNameMatcher.Kind.NOT_FOUND, PluginNameMatcher.find(NAMES, null).kind);
		assertEquals(PluginNameMatcher.Kind.NOT_FOUND, PluginNameMatcher.find(NAMES, "zzz").kind);
	}
}
