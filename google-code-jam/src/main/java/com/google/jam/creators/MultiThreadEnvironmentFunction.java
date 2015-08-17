package com.google.jam.creators;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

import java.util.Collection;
import java.util.function.Function;

public class MultiThreadEnvironmentFunction
        implements Function<Collection<String>, LastIndexTaskQueue<String>> {

    @Override
    public LastIndexTaskQueue<String> apply(final Collection<String> data) {
        return new LastIndexTaskLinkedBlockingQueue<>(data);
    }
}
