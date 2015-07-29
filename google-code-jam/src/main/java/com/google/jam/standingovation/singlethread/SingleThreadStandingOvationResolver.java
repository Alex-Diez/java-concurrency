package com.google.jam.standingovation.singlethread;

import java.util.HashMap;
import java.util.Map;

import com.google.jam.Round;
import com.google.jam.standingovation.StandingOvationResolver;

public class SingleThreadStandingOvationResolver
		implements StandingOvationResolver {

	@Override
	public Map<Integer, Integer> solve(Round round) {
		final Map<Integer, Integer> results = new HashMap<>(round.numberOfTasks(), 1.0f);
		String taskString = round.getNextTask();
		int taskCounter = 1;
		while (taskString != null) {
			final int index = taskCounter++;
			final String task = taskString;
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
			taskString = round.getNextTask();
		}
		return results;
	}

	@Override
	public void close()
			throws Exception {
	}
}
