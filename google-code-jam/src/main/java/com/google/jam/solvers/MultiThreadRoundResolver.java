package com.google.jam.solvers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.jam.Round;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class MultiThreadRoundResolver
        extends AbstractRoundResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadRoundResolver.class);

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
//                    LOGGER.debug("Task is - {}", task);
                    final int index = round.getLastTaskId();
//                    LOGGER.debug("Task index is - {}", index);
                    if(task != null) {
                        final int result = doCalculation(task, algorithm);
                        results.put(index, result);
                    }
                }
        );
    }
}
