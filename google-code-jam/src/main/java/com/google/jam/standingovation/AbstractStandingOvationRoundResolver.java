package com.google.jam.standingovation;

import java.util.Map;
import java.util.function.Function;

import com.google.jam.Round;
import com.google.jam.RoundResolver;

public abstract class AbstractStandingOvationRoundResolver
		implements RoundResolver {

	@Override
	public Map<Integer, Integer> solve(final Round round, final Function<String, Integer> algorithm) {
		final Map<Integer, Integer> results = buildCollectionOfResults(round);
		resetTaskCounter();
		while (round.hasNextTask()) {
			runCalculation(results, round, algorithm);
		}
		timeOut();
		return results;
	}

	protected abstract Map<Integer, Integer> buildCollectionOfResults(Round round);

	protected abstract void resetTaskCounter();

	protected abstract void runCalculation(
			final Map<Integer, Integer> results,
			final Round round,
			final Function<String, Integer> algorithm);

	protected abstract void timeOut();

	protected void doCalculation(
			final Map<Integer, Integer> results,
			final int index,
			final String task,
			final Function<String, Integer> algorithm) {
		int counter = algorithm.apply(task);
		results.put(index, counter);
	}

	public static final class BackwardCountingAlgorithm
			implements Function<String, Integer> {

		@Override
		public Integer apply(String task) {
			final String[] array = task.split("\\s+");
			final int last = Integer.parseInt(array[0]);
			final String audience = array[1];
			int value = last;
			for (int currentShineLevel = last - 1; currentShineLevel > -1; currentShineLevel--) {
				final int currentValue = Character.digit(audience.charAt(currentShineLevel), 10);
				value -= currentValue;
				if (value < 0) {
					value = currentShineLevel;
				}
			}
			return value > -1 ? value : 0;
		}
	}

	public static final class ForwardCountingAlgorithm
			implements Function<String, Integer> {

		@Override
		public Integer apply(String task) {
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
			return counter;
		}
	}
}
