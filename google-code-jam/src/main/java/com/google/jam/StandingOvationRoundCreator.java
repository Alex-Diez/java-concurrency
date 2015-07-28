package com.google.jam;

import java.util.List;

public class StandingOvationRoundCreator
		implements RoundCreator {

	@Override
	public Round createRound(List<String> strings)
			throws WrongRoundFormatException {
		final int queueLength;
		try {
			final String length = strings.remove(0);
			queueLength = Integer.parseInt(length);
		}
		catch (NumberFormatException e) {
			throw new WrongRoundFormatException();
		}
		return new Round(queueLength, strings);
	}
}
