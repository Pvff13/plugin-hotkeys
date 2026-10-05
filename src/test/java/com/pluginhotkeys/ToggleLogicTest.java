package com.pluginhotkeys;

import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ToggleLogicTest
{
	@Test
	public void splitsOnCommasAndDropsBlanks()
	{
		assertEquals(Arrays.asList("Ground Items", "xp tracker", "npc"), ToggleLogic.splitQueries(" Ground Items, xp tracker ,, ,npc,"));
		assertEquals(Arrays.asList("solo"), ToggleLogic.splitQueries("solo"));
		assertEquals(0, ToggleLogic.splitQueries("").size());
		assertEquals(0, ToggleLogic.splitQueries(null).size());
	}

	@Test
	public void syncFollowsTheFirstPluginWhenTheyDisagree()
	{
		// first is on, second is off: everything goes off
		assertArrayEquals(new boolean[]{false, false}, ToggleLogic.desiredStates(new boolean[]{true, false}, MultiPluginMode.SYNC));
		// first is off, second is on: everything goes on
		assertArrayEquals(new boolean[]{true, true}, ToggleLogic.desiredStates(new boolean[]{false, true}, MultiPluginMode.SYNC));
	}

	@Test
	public void flipSwapsEachPluginOnItsOwn()
	{
		assertArrayEquals(new boolean[]{false, true, false}, ToggleLogic.desiredStates(new boolean[]{true, false, true}, MultiPluginMode.FLIP));
	}

	@Test
	public void aSinglePluginBehavesTheSameInBothModes()
	{
		assertArrayEquals(new boolean[]{true}, ToggleLogic.desiredStates(new boolean[]{false}, MultiPluginMode.SYNC));
		assertArrayEquals(new boolean[]{true}, ToggleLogic.desiredStates(new boolean[]{false}, MultiPluginMode.FLIP));
	}
}
