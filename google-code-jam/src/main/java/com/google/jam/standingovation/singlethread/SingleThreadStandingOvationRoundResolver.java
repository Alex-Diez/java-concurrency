package com.google.jam.standingovation.singlethread;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.google.jam.Round;
import com.google.jam.standingovation.AbstractStandingOvationRoundResolver;

public class SingleThreadStandingOvationRoundResolver
		extends AbstractStandingOvationRoundResolver {

	private int indexCounter = 1;

	@Override
	protected Map<Integer, Integer> buildCollectionOfResults(final Round round) {
		return new HashMap<>(round.numberOfTasks(), 1.0f);
	}

	@Override
	protected void resetTaskCounter() {
		indexCounter = 1;
	}

	@Override
	protected void runCalculation(
			final Map<Integer, Integer> results,
			final Round round,
			final Function<String, Integer> algorithm) {
		doCalculation(results, indexCounter++, round.getNextTask(), algorithm);
	}

	@Override
	protected void timeOut() {
	}
}
