package com.google.jam.creators;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.jam.Round;
import com.google.jam.creators.MultiThreadTaskQueueSupplier;
import com.google.jam.creators.SingleThreadTaskQueueSupplier;

public class RoundCreator {

    public Round createRound(
            final List<String> strings,
            final Function<List<String>, Map<Integer, String>> roundFunction) {
        final Map<Integer, String> tasks = roundFunction.apply(strings);
        return new Round(new SingleThreadTaskQueueSupplier(tasks));
    }

    public Round createRoundForMultiThreadEnvironment(
            final List<String> strings,
            final Function<List<String>, Map<Integer, String>> roundFunction) {
        final Map<Integer, String> tasks = roundFunction.apply(strings);
        return new Round(new MultiThreadTaskQueueSupplier(tasks));
    }
}
