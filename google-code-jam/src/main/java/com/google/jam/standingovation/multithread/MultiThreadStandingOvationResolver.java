package com.google.jam.standingovation.multithread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import com.google.jam.Round;
import com.google.jam.standingovation.StandingOvationResolver;

import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class MultiThreadStandingOvationResolver
		implements StandingOvationResolver {

	private static final int NUMBER_OF_THREADS = getRuntime().availableProcessors() * 2;

	private final ExecutorService executor;

	public MultiThreadStandingOvationResolver() {
		this.executor = newFixedThreadPool(NUMBER_OF_THREADS);
	}

	public Map<Integer, Integer> solve(final Round round) {
		final Map<Integer, Integer> asynchronousResults = new ConcurrentHashMap<>(round.numberOfTasks(), 1.0f);
		String taskString = round.getNextTask();
		int taskCounter = 1;
		while (taskString != null) {
			final int index = taskCounter++;
			final String task = taskString;
			executor.submit(
					() -> {
						int counter = 0;
						int previousCounter = 0;
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
						asynchronousResults.put(index, counter);
					}
			);
			taskString = round.getNextTask();
		}
		return asynchronousResults;
	}

	@Override
	public void close()
			throws Exception {
		executor.shutdown();
	}
}
