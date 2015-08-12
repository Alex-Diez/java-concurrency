package com.google.jam.solvers;

import java.util.Map;
import java.util.function.Function;

import com.google.jam.Round;

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

    protected int doCalculation(
            final String task,
            final Function<String, Integer> algorithm) {
        return algorithm.apply(task);
    }
}
