package com.google.jam.algorithms.standingovation;

import java.util.function.Function;

public final class ForwardCountingAlgorithm
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
