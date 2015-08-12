package com.google.jam.solvers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.jam.Round;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class MultiThreadRoundResolver
        extends AbstractRoundResolver {

    private final ExecutorService executor;

    public MultiThreadRoundResolver(Supplier<Integer> numberOfThreadSupplier) {
        this.executor = newFixedThreadPool(numberOfThreadSupplier.get());
    }

    @Override
    public void shutDownResolver() {
        executor.shutdown();
    }

    protected Map<Integer, Integer> buildCollectionOfResults(Round round) {
        return new ConcurrentHashMap<>(round.numberOfTasks(), 1.0f);
    }

    @Override
    protected void runCalculation(
            final Map<Integer, Integer> results,
            final Round round,
            final Function<String, Integer> algorithm) {
        executor.execute(
                () -> {
                    final String task = round.getNextTask();
                    final int index = round.getLastTaskId();
                    final int result = doCalculation(task, algorithm);
                    results.put(index, result);
                }
        );
    }
}
