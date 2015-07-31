package com.google.jam.standingovation.multithread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

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
	private final AtomicInteger indexCounter = new AtomicInteger(1);

	public MultiThreadStandingOvationRoundResolver() {
		this.executor = newFixedThreadPool(NUMBER_OF_THREADS);
	}

	@Override
	public void shutdownThreadPool() {
		executor.shutdown();
	}

	protected Map<Integer, Integer> buildCollectionOfResults(Round round) {
		return new ConcurrentHashMap<>(round.numberOfTasks(), 1.0f);
	}

	@Override
	protected void resetTaskCounter() {
		indexCounter.set(1);
	}

	@Override
	protected void runCalculation(
			final Map<Integer, Integer> results,
			final Round round,
			final Function<String, Integer> algorithm) {
		executor.execute(
				() -> {
					int index = indexCounter.getAndIncrement();
					String task = round.getNextTask();
					if (task != null) {
						doCalculation(results, index, task, algorithm);
					}
				}
		);
	}

	@Override
	protected void timeOut() {
		try {
			Thread.sleep((long) (1.0 / (NUMBER_OF_THREADS - 1) * 90));
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
