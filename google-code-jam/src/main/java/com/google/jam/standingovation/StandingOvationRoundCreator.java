package com.google.jam.standingovation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
		return new Round(parallelism, strings);
	}
}
