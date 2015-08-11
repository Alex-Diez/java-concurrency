package com.google.jam.creators;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

public class MultiThreadTaskQueueSupplier
        implements Supplier<Queue<Map.Entry<Integer, String>>> {

    private final Map<Integer, String> data;

    public MultiThreadTaskQueueSupplier(Map<Integer, String> data) {
        this.data = data;
    }

    @Override
    public Queue<Map.Entry<Integer, String>> get() {
        return new LinkedBlockingQueue<>(data.entrySet());
    }
}
