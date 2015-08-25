package com.google.jam.solvers;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.jam.Round;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class MultiThreadRoundResolver
        implements RoundResolver {
    
    private final ExecutorService executor;
    private final Lock lock;
    private final AtomicInteger taskCounter;

    public MultiThreadRoundResolver(final Supplier<Integer> numberOfThreadSupplier) {
        this.executor = newFixedThreadPool(numberOfThreadSupplier.get());
        this.lock = new ReentrantLock();
        this.taskCounter = new AtomicInteger();
    }

    @Override
    public void shutDownResolver() {
        executor.shutdown();
    }

    @Override
    public Map<Integer, String> solve(final Round round, final Function<String, String> algorithm) {
        resetCounters();
        final Map<Integer, String> results = buildCollectionOfResults(round);
        submitAllTasks(round, algorithm, results);
        waitForCompletion(results, round.numberOfTasks());
        final int resultsSize = results.size();
        final int roundNumberOfTasks = round.numberOfTasks();
        assert resultsSize == roundNumberOfTasks
                : "Results should have size " + roundNumberOfTasks + " but is " + resultsSize;
        return results;
    }

    private void resetCounters() {
        taskCounter.set(0);
    }

    private Map<Integer, String> buildCollectionOfResults(final Round round) {
        return new ConcurrentHashMap<>(round.numberOfTasks(), 1.0f);
    }

    private void submitAllTasks(
            final Round round,
            final Function<String, String> algorithm,
            final Map<Integer, String> results) {
        Callable<Void> callable = buildTask(round, algorithm, results);
        Collection<Callable<Void>> callableCollection = Collections.nCopies(round.numberOfTasks(), callable);
        try {
            executor.invokeAll(callableCollection);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Callable<Void> buildTask(
            final Round round,
            final Function<String, String> algorithm,
            final Map<Integer, String> results) {
        return () -> {
            String task;
            int index;
            lock.lock();
            try {
                task = round.getNextTask();
                index = taskCounter.incrementAndGet();
            }
            finally {
                lock.unlock();
            }
            final String result = algorithm.apply(task);
            results.put(index, result);
            return null;
        };
    }

    private void waitForCompletion(final Map<Integer, String> results, final int numberOfTasks) {
        while (results.size() < numberOfTasks) {
        }
    }
}
