package com.google.jam.solvers;

import com.google.jam.Round;

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

import static java.util.concurrent.Executors.newFixedThreadPool;

public class MultiThreadRoundResolver
        implements RoundResolver {

    private final ExecutorService executor;
    private final Lock lock;
    private final AtomicInteger taskCounter;

    public MultiThreadRoundResolver(Supplier<Integer> numberOfThreadSupplier) {
        this.executor = newFixedThreadPool(numberOfThreadSupplier.get());
        this.lock = new ReentrantLock();
        this.taskCounter = new AtomicInteger();
    }

    @Override
    public void shutDownResolver() {
        executor.shutdown();
    }

    private Map<Integer, Integer> buildCollectionOfResults(Round round) {
        return new HashMap<>(round.numberOfTasks());
    }

    @Override
    public Map<Integer, Integer> solve(final Round round, final Function<String, Integer> algorithm) {
        taskCounter.set(0);
        final Map<Integer, Integer> results = buildCollectionOfResults(round);
        Callable<Integer> callable = () -> {
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
            return null;
        };
        Collection<Callable<Integer>> callableCollection = Collections.nCopies(round.numberOfTasks(), callable);
        try {
            executor.invokeAll(callableCollection);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        while (taskCounter.get() < round.numberOfTasks()) {
        }
        return results;
    }
}
