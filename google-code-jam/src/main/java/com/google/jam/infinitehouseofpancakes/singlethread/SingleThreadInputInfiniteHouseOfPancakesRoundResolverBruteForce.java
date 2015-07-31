package com.google.jam.infinitehouseofpancakes.singlethread;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.google.jam.Round;
import com.google.jam.RoundResolver;

public class SingleThreadInputInfiniteHouseOfPancakesRoundResolverBruteForce
		implements RoundResolver {

	public static final int MIN_NUMBER_TO_DIVIDE = 3;

	@Override
	public Map<Integer, Integer> solve(final Round round, final Function<String, Integer> algorithm) {
		final Map<Integer, Integer> results = new HashMap<>(round.numberOfTasks());
		String task = round.getNextTask();
		int taskCounter = 1;
		while (task != null) {
			final int index = taskCounter++;
			int[] valuesOfPancakes = convertRoundTaskToNumbers(task);
			Arrays.sort(valuesOfPancakes);
			int max = valuesOfPancakes[valuesOfPancakes.length - 1];
			if (max < MIN_NUMBER_TO_DIVIDE) {
				results.put(index, max);
			}
			else {
				int counter = 0;
				boolean needToDivide = true;
				while (needToDivide) {
					max = valuesOfPancakes[valuesOfPancakes.length - 1];
					int hasLeftPancakes = max / 2;
					int givenPancakes = max - hasLeftPancakes;
					int[] buffer = new int[valuesOfPancakes.length + 1];
					System.arraycopy(valuesOfPancakes, 0, buffer, 0, valuesOfPancakes.length - 1);
					buffer[valuesOfPancakes.length - 1] = hasLeftPancakes;
					buffer[valuesOfPancakes.length] = givenPancakes;
					Arrays.sort(buffer);
					valuesOfPancakes = buffer;
					counter++;
					max = valuesOfPancakes[valuesOfPancakes.length - 1];
					needToDivide = (max + 1) / 2 + 1 < max;
				}
				counter += max;
				results.put(index, counter);
			}
			task = round.getNextTask();
		}
		return results;
	}

	private int[] convertRoundTaskToNumbers(final String task) {
		final String[] data = task.split("\\s+");
		final int startedLength = Integer.parseInt(data[0]);
		int[] array = new int[startedLength];
		for (int i = 1; i < data.length; i++) {
			array[i - 1] = Integer.parseInt(data[i]);
		}
		return array;
	}
}
