package com.google.jam.solvers;

import com.google.jam.Round;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SingleThreadRoundResolver
        implements RoundResolver {

    private Map<Integer, String> buildCollectionOfResults(final Round round) {
        return new HashMap<>(round.numberOfTasks(), 1.0f);
    }

    @Override
    public Map<Integer, String> solve(final Round round, final Function<String, String> algorithm) {
        final Map<Integer, String> results = buildCollectionOfResults(round);
        int taskCounter = 0;
        while (round.hasNextTask()) {
            final String result = algorithm.apply(round.getNextTask());
            final int index = ++taskCounter;
            results.put(index, result);
        }
        assert results.size() == round.numberOfTasks()
                : "Results should have size " + round.numberOfTasks() + " but has " + results;
        return results;
    }

    @Override
    public void shutDownResolver() {
    }
}
