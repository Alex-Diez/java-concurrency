package com.google.jam.creators;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Supplier;

public class SingleThreadTaskQueueSupplier
        implements Supplier<Queue<Map.Entry<Integer, String>>> {

    private final Map<Integer, String> data;

    public SingleThreadTaskQueueSupplier(Map<Integer, String> data) {
        this.data = data;
    }

    @Override
    public Queue<Map.Entry<Integer, String>> get() {
        return new LinkedList<>(data.entrySet());
    }
}
