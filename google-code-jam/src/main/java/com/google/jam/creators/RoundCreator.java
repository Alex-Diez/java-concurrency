package com.google.jam.creators;

import com.google.jam.Round;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.function.Function;

public class RoundCreator {

    public Round createRound(
            final List<String> strings,
            final Function<List<String>, Map<Integer, String>> roundFunction,
            final Function<Map<Integer, String>, Queue<Entry<Integer, String>>> threadEnvironmentFunction) {
        final Map<Integer, String> tasks = roundFunction.apply(strings);
        return new Round(threadEnvironmentFunction.apply(tasks));
    }
}
