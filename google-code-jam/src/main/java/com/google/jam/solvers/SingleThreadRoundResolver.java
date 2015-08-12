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
        final Map.Entry<Integer, String> task = round.getNextTask();
        final int index = task.getKey();
        final String data = task.getValue();
        final int result = doCalculation(data, algorithm);
        results.put(index, result);
    }
}
