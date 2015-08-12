package com.google.jam.solvers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.google.jam.Round;

public class SingleThreadRoundResolver
        extends AbstractRoundResolver {

    @Override
    protected Map<Integer, Integer> buildCollectionOfResults(final Round round) {
        return new HashMap<>(round.numberOfTasks(), 1.0f);
    }

    @Override
    protected void runCalculation(
            final Map<Integer, Integer> results,
            final Round round,
            final Function<String, Integer> algorithm) {
        final String task = round.getNextTask();
        final int index = round.getLastTaskId();
        final int result = doCalculation(task, algorithm);
        results.put(index, result);
    }

    @Override
    public void shutDownResolver() {
    }
}
