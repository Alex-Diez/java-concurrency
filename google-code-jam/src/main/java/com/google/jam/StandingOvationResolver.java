package com.google.jam;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class StandingOvationResolver
		implements AutoCloseable {

	private static final int NUMBER_OF_THREADS = getRuntime().availableProcessors() * 2;

	private final ExecutorService executor;
	private final SortedMap<Integer, Integer> results;
	private int counter = 1;

	public StandingOvationResolver(final boolean parallel) {
		if (parallel) {
			this.executor = newFixedThreadPool(NUMBER_OF_THREADS);
			this.results = new ConcurrentSkipListMap<>();
		}
		else {
			this.executor = newSingleThreadExecutor();
			this.results = new TreeMap<>();
		}
	}

	public Map<Integer, Integer> solve(final Round round) {
		Map<Integer, Future<Integer>> asynchronousResults = new HashMap<>();
		String taskString = round.getNextTask();
		while (taskString != null) {
			final int index = counter++;
			final String task = taskString;
			Future<Integer> future = executor.submit(
					() -> {
						int counter = 0;
						int previousCounter = 0;
						int allPeople = 0;
						final String audience = task.split("\\s+")[1];
						int value = Character.digit(audience.charAt(0), 10);
						for (int currentShineLevel = 1; currentShineLevel < audience.length(); currentShineLevel++) {
							previousCounter = counter;
							allPeople += value;
							counter += currentShineLevel - allPeople;
							if(previousCounter < counter) {
								allPeople += counter - previousCounter;
							}
							value = Character.digit(audience.charAt(currentShineLevel), 10);
						}
						return counter;
					}
			);
			asynchronousResults.put(index, future);
			taskString = round.getNextTask();
		}
		Map<Integer, Integer> results = new TreeMap<>();
		for(Map.Entry<Integer, Future<Integer>> result : asynchronousResults.entrySet()) {
			try {
				results.put(result.getKey(), result.getValue().get());
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return results;
	}

	@Override
	public void close()
			throws Exception {
		executor.shutdown();
	}
}
