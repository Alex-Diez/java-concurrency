package com.google.jam;

import java.util.List;
import java.util.Map;

import com.google.jam.creators.MultiThreadTaskQueueSupplier;
import com.google.jam.creators.SingleThreadTaskQueueSupplier;

public abstract class RoundCreator {

    public Round createRound(final List<String> strings) {
        final Map<Integer, String> tasks = getIntegerStringMap(strings);
        return new Round(new SingleThreadTaskQueueSupplier(tasks));
    }

    public Round createRoundForMultiThreadEnvironment(final List<String> strings) {
        final Map<Integer, String> tasks = getIntegerStringMap(strings);
        return new Round(new MultiThreadTaskQueueSupplier(tasks));
    }

    protected abstract Map<Integer,String> getIntegerStringMap(final List<String> strings);
}
