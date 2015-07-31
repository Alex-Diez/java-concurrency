package com.google.jam.standingovation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.WrongRoundFormatException;

public class StandingOvationRoundCreator
		implements RoundCreator {

	private final boolean parallelism;

	public StandingOvationRoundCreator(final boolean parallelism) {
		this.parallelism = parallelism;
	}

	@Override
	public Round createRound(final List<String> strings)
			throws WrongRoundFormatException {
		final int queueLength;
		try {
			final String length = strings.remove(0);
			queueLength = Integer.parseInt(length);
		}
		catch (NumberFormatException e) {
			throw new WrongRoundFormatException();
		}
		if(queueLength != strings.size()) {
			throw new WrongRoundFormatException();
		}
		Map<Integer, String> tasks = new HashMap<>(strings.size());
		for (int i = 0; i < strings.size(); i++) {
			tasks.put(i+1, strings.get(i));
		}
		return new Round(parallelism, tasks);
	}
}
