package com.google.jam.infinitehouseofpancakes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.WrongRoundFormatException;

public class InfiniteHouseOfPancakesRoundCreator
		implements RoundCreator {

	@Override
	public Round createRound(final List<String> strings) {
		final int queueLength;
		try {
			final String length = strings.remove(0);
			queueLength = Integer.parseInt(length);
		}
		catch (NumberFormatException e) {
			throw new WrongRoundFormatException();
		}
		if (queueLength != strings.size() / 2) {
			throw new WrongRoundFormatException();
		}
		Collection<String> tasks = new ArrayList<>(strings.size() / 2);
		for (int i = 0; i < strings.size() - 1; i += 2) {
			tasks.add(strings.get(i) + '\n' + strings.get(i + 1));
		}
		return new Round(false, queueLength, tasks);
	}
}
