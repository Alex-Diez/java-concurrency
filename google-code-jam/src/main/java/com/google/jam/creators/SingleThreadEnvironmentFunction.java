package com.google.jam.creators;

import com.google.jam.datastructures.LastIndexTaskLinkedQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

import java.util.Collection;
import java.util.function.Function;

public class SingleThreadEnvironmentFunction
        implements Function<Collection<String>, LastIndexTaskQueue<String>> {

    @Override
    public LastIndexTaskQueue<String> apply(final Collection<String> data) {
        return new LastIndexTaskLinkedQueue<>(data);
    }
}
