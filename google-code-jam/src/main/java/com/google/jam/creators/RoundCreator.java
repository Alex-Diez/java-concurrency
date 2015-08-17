package com.google.jam.creators;

import com.google.jam.Round;
import com.google.jam.datastructures.LastIndexTaskQueue;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class RoundCreator {

    public Round createRound(
            final List<String> strings,
            final Function<List<String>, Collection<String>> roundFunction,
            final Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction) {
        final Collection<String> tasks = roundFunction.apply(strings);
        return new Round(threadEnvironmentFunction.apply(tasks));
    }
}
