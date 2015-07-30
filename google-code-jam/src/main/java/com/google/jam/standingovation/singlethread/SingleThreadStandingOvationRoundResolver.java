package com.google.jam.standingovation.singlethread;

import java.util.HashMap;
import java.util.Map;

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
	protected void runCalculation(final Map<Integer, Integer> results, final Round round) {
		doCalculation(results, indexCounter++, round.getNextTask());
	}

	@Override
	protected void timeOut() {
	}
}
