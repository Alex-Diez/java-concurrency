package com.google.jam.standingovation.singlethread;

import java.util.HashMap;
import java.util.Map;

import com.google.jam.Round;
import com.google.jam.standingovation.StandingOvationResolver;

public class SingleThreadStandingOvationResolver
		extends StandingOvationResolver {

	@Override
	protected void runCalculation(Map<Integer, Integer> results, int index, String task) {
		doCalculation(results, index, task);
	}

	@Override
	protected Map<Integer, Integer> buildCollectionOfResults(Round round) {
		return new HashMap<>(round.numberOfTasks(), 1.0f);
	}

	@Override
	public void close()
			throws Exception {
	}
}
