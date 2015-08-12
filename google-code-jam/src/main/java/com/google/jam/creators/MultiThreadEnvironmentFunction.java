package com.google.jam.creators;

import com.google.jam.datastructures.LastIndexLinkedTaskBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

import java.util.Map;
import java.util.function.Function;

public class MultiThreadEnvironmentFunction
        implements Function<Map<Integer, String>, LastIndexTaskQueue<String>> {

    @Override
    public LastIndexTaskQueue<String> apply(final Map<Integer, String> data) {
        return new LastIndexLinkedTaskBlockingQueue<>(data.values());
    }
}
