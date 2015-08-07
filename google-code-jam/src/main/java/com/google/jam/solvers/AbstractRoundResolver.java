package com.google.jam.solvers;

import java.util.Map;
import java.util.function.Function;

import com.google.jam.Round;
import com.google.jam.RoundResolver;

public abstract class AbstractRoundResolver
        implements RoundResolver {

    @Override
    public Map<Integer, Integer> solve(final Round round, final Function<String, Integer> algorithm) {
        final Map<Integer, Integer> results = buildCollectionOfResults(round);
        while (round.hasNextTask()) {
            runCalculation(results, round, algorithm);
        }
        return results;
    }

    protected abstract Map<Integer, Integer> buildCollectionOfResults(Round round);

    protected abstract void runCalculation(
            final Map<Integer, Integer> results,
            final Round round,
            final Function<String, Integer> algorithm);

    protected void doCalculation(
            final Map<Integer, Integer> results,
            final int index,
            final String task,
            final Function<String, Integer> algorithm) {
        int counter = algorithm.apply(task);
        results.put(index, counter);
    }
}
