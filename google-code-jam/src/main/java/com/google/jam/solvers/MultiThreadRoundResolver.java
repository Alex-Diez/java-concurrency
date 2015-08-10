package com.google.jam.solvers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

import com.google.jam.Round;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class MultiThreadRoundResolver
        extends AbstractRoundResolver {

    private final ExecutorService executor;

    public MultiThreadRoundResolver(Function<Void, Integer> numberOfThreadFunction) {
        this.executor = newFixedThreadPool(numberOfThreadFunction.apply(null));
    }

    public void shutdownThreadPool() {
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
                    final Map.Entry<Integer, String> task = round.getNextTask();
                    if (task != null) {
                        final int index = task.getKey();
                        final String data = task.getValue();
                        doCalculation(results, index, data, algorithm);
                    }
                }
        );
    }
}
