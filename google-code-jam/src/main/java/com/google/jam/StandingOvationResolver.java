package com.google.jam;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class StandingOvationResolver
		implements AutoCloseable {

	private static final int NUMBER_OF_THREADS = getRuntime().availableProcessors() * 2;

	private final ExecutorService executor;

	public StandingOvationResolver(final boolean parallel) {
		this.executor = parallel ? newFixedThreadPool(NUMBER_OF_THREADS) : newSingleThreadExecutor();
	}

	public Map<Integer, Integer> solve(final Round round) {
		final Map<Integer, Integer> asynchronousResults =
				round.inParallel()
						? new ConcurrentHashMap<>(round.numberOfTasks())
						: new HashMap<>(round.numberOfTasks());
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
						System.out.println(
								"Tread " + Thread.currentThread().getId() + " size - " + asynchronousResults.size()
						);
					}
			);
			taskString = round.getNextTask();
		}
		int size;
		do {
			size = asynchronousResults.size();
			System.out.println("Size - " + size);
		} while (size < round.numberOfTasks());
		return asynchronousResults;
	}

	@Override
	public void close()
			throws Exception {
		executor.shutdown();
	}
}
