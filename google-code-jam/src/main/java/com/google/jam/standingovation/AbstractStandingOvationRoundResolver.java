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

	public static final class StandingOvationContestAnalysisAlgorithm
			implements Function<String, Integer> {

		@Override
		public Integer apply(String task) {
			final String[] array = task.split("\\s+");
			final int shineMaxLevel = Integer.parseInt(array[0]);
			final String audience = array[1];
			int temp = 0;
			int value = 0;
			for (int currentShineLevel = 0; currentShineLevel < shineMaxLevel + 1; currentShineLevel++) {
				value = Math.max(value, currentShineLevel - temp);
				temp += audience.charAt(currentShineLevel) - '0';
			}
			return value;
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
			int value = retrieveIntFromChar(audience, 0);
			for (int currentShineLevel = 1; currentShineLevel < audience.length(); currentShineLevel++) {
				previousCounter = counter;
				allPeople += value;
				final int needNewPeople = needNewPeople(allPeople, currentShineLevel);
				counter += needNewPeople > 0 ? needNewPeople : 0;
				if (previousCounter < counter) {
					allPeople += counter - previousCounter;
				}
				value = retrieveIntFromChar(audience, currentShineLevel);
			}
			return counter;
		}

		private int retrieveIntFromChar(String audience, int currentShineLevel) {
			return audience.charAt(currentShineLevel) - '0';
		}

		private int needNewPeople(final int allPeople, final int currentShineLevel) {
			return currentShineLevel - allPeople;
		}
	}
}
