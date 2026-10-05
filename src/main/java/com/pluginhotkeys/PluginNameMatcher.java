package com.pluginhotkeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Finds the plugin a typed name refers to. Matching ignores case and punctuation, and tries, in order:
 * the whole name, then names that start with the text, then names that contain it. The first tier with
 * any hit decides, so "ground" can't be hijacked by a longer name once "Ground Items" starts with it,
 * but two plugins in the same tier make the text ambiguous rather than picking one for you.
 */
final class PluginNameMatcher
{
	enum Kind
	{
		NOT_FOUND,
		FOUND,
		AMBIGUOUS
	}

	static final class Result
	{
		final Kind kind;
		/** Index into the searched names; only meaningful when kind is FOUND. */
		final int index;
		/** Every name in the deciding tier; only meaningful when kind is AMBIGUOUS. */
		final List<String> candidates;

		private Result(Kind kind, int index, List<String> candidates)
		{
			this.kind = kind;
			this.index = index;
			this.candidates = candidates;
		}
	}

	static Result find(List<String> names, String query)
	{
		String wanted = normalize(query);
		if (wanted.isEmpty())
		{
			return new Result(Kind.NOT_FOUND, -1, new ArrayList<>());
		}

		List<String> normalized = new ArrayList<>(names.size());
		for (String name : names)
		{
			normalized.add(normalize(name));
		}

		for (int tier = 0; tier < 3; tier++)
		{
			List<Integer> hits = new ArrayList<>();
			for (int i = 0; i < normalized.size(); i++)
			{
				String candidate = normalized.get(i);
				boolean hit = tier == 0 ? candidate.equals(wanted)
					: tier == 1 ? candidate.startsWith(wanted)
					: candidate.contains(wanted);
				if (hit)
				{
					hits.add(i);
				}
			}

			if (hits.size() == 1 || (tier == 0 && !hits.isEmpty()))
			{
				return new Result(Kind.FOUND, hits.get(0), new ArrayList<>());
			}
			if (hits.size() > 1)
			{
				List<String> candidates = new ArrayList<>();
				for (int i : hits)
				{
					candidates.add(names.get(i));
				}
				return new Result(Kind.AMBIGUOUS, -1, candidates);
			}
		}

		return new Result(Kind.NOT_FOUND, -1, new ArrayList<>());
	}

	/** Lower case, letters and digits only, single spaces. */
	static String normalize(String text)
	{
		if (text == null)
		{
			return "";
		}
		return text.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", " ").trim();
	}

	private PluginNameMatcher()
	{
	}
}
