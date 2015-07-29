package com.google.jam.standingovation;

import java.util.Map;

import com.google.jam.Round;
import com.google.jam.RoundResolver;

public abstract class AbstractStandingOvationRoundResolver
		implements RoundResolver {

	@Override
	public Map<Integer, Integer> solve(final Round round) {
		final Map<Integer, Integer> results = buildCollectionOfResults(round);
		String taskString = round.getNextTask();
		int taskCounter = 1;
		while (taskString != null) {
			final int index = taskCounter++;
			final String task = taskString;
			runCalculation(results, index, task);
			taskString = round.getNextTask();
		}
		timeOut();
		return results;
	}

	protected abstract void timeOut();

	protected abstract void runCalculation(final Map<Integer, Integer> results, final int index, final String task);

	protected void doCalculation(final Map<Integer, Integer> results, final int index, final String task) {
		int counter = 0;
		int previousCounter;
		int allPeople = 0;
		final String audience = task.split("\\s+")[1];
		int value = Character.digit(audience.charAt(0), 10);
		for (int currentShineLevel = 1; currentShineLevel < audience.length(); currentShineLevel++) {
			previousCounter = counter;
			allPeople += value;
			counter += currentShineLevel - allPeople > 0 ? currentShineLevel - allPeople : 0;
			if (previousCounter < counter) {
				allPeople += counter - previousCounter;
			}
			value = Character.digit(audience.charAt(currentShineLevel), 10);
		}
		results.put(index, counter);
	}

	protected abstract Map<Integer, Integer> buildCollectionOfResults(Round round);
}
