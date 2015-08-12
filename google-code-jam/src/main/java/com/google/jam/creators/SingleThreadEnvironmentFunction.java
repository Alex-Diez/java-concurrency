package com.google.jam.creators;

import com.google.jam.datastructures.LastIndexTaskLinkedQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;

public class SingleThreadEnvironmentFunction
        implements Function<Map<Integer, String>, LastIndexTaskQueue<String>> {

    @Override
    public LastIndexTaskQueue<String> apply(final Map<Integer, String> data) {
        return new LastIndexTaskLinkedQueue<>(data.values());
    }
}
