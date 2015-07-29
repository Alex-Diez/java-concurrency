package com.google.jam.standingovation.singlethread;

import java.util.HashMap;
import java.util.Map;

import com.google.jam.Round;
import com.google.jam.standingovation.AbstractStandingOvationRoundResolver;

public class SingleThreadStandingOvationRoundResolver
		extends AbstractStandingOvationRoundResolver {

	@Override
	protected void runCalculation(Map<Integer, Integer> results, int index, String task) {
		doCalculation(results, index, task);
	}

	@Override
	protected Map<Integer, Integer> buildCollectionOfResults(Round round) {
		return new HashMap<>(round.numberOfTasks(), 1.0f);
	}

	@Override
	protected void timeOut() {
	}
}
