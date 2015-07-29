package com.google.jam.standingovation.multithread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import com.google.jam.MultiThreadRoundResolver;
import com.google.jam.Round;
import com.google.jam.standingovation.AbstractStandingOvationRoundResolver;

import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class MultiThreadStandingOvationRoundResolver
		extends AbstractStandingOvationRoundResolver
		implements MultiThreadRoundResolver {

	private static final int NUMBER_OF_THREADS = getRuntime().availableProcessors() * 2;

	private final ExecutorService executor;

	public MultiThreadStandingOvationRoundResolver() {
		this.executor = newFixedThreadPool(NUMBER_OF_THREADS);
	}

	@Override
	protected void runCalculation(final Map<Integer, Integer> results, final int index, final String task) {
		executor.execute(() -> doCalculation(results, index, task));
	}

	protected Map<Integer, Integer> buildCollectionOfResults(Round round) {
		return new ConcurrentHashMap<>(round.numberOfTasks(), 1.0f);
	}

	@Override
	public void shutdownThreadPool() {
		executor.shutdown();
	}
}
