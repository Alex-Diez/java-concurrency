package com.google.jam.solvers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
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
    private final AtomicInteger completedTaskCounter;

    public MultiThreadRoundResolver(final Supplier<Integer> numberOfThreadSupplier) {
        this.executor = newFixedThreadPool(numberOfThreadSupplier.get());
        this.lock = new ReentrantLock();
        this.taskCounter = new AtomicInteger();
        this.completedTaskCounter = new AtomicInteger();
    }

    @Override
    public void shutDownResolver() {
        executor.shutdown();
    }

    private Map<Integer, Integer> buildCollectionOfResults(final Round round) {
        return new HashMap<>(round.numberOfTasks(), 1.0f);
    }

    @Override
    public Map<Integer, Integer> solve(final Round round, final Function<String, Integer> algorithm) {
        resetCounters();
        final Map<Integer, Integer> results = buildCollectionOfResults(round);
        submitAllTasks(round, algorithm, results);
        waitForCompletion(round.numberOfTasks());
        assert results.size() == round.numberOfTasks()
                : "Results should have size " + round.numberOfTasks() + " but has " + results;
        return results;
    }

    private void waitForCompletion(final int numberOfTasks) {
        while (completedTaskCounter.get() < numberOfTasks) {
        }
    }

    private void submitAllTasks(
            final Round round,
            final Function<String, Integer> algorithm,
            final Map<Integer, Integer> results) {
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
            final Function<String, Integer> algorithm,
            final Map<Integer, Integer> results) {
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
            final int result = algorithm.apply(task);
            results.put(index, result);
            completedTaskCounter.incrementAndGet();
            return null;
        };
    }

    private void resetCounters() {
        taskCounter.set(0);
        completedTaskCounter.set(0);
    }
}
