package com.google.jam.infinitehouseofpancakes.singlethread;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.google.jam.Round;
import com.google.jam.RoundResolver;

public class SingleThreadInputInfiniteHouseOfPancakesRoundResolverBruteForce
        implements RoundResolver {

    @Override
    public Map<Integer, Integer> solve(final Round round, final Function<String, Integer> algorithm) {
        final Map<Integer, Integer> results = new HashMap<>(round.numberOfTasks());
        Map.Entry<Integer, String> task = round.getNextTask();
        while (task != null) {
            final int index = task.getKey();
            final int counter = algorithm.apply(task.getValue());
            results.put(index, counter);
            task = round.getNextTask();
        }
        return results;
    }

}
