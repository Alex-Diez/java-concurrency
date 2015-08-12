package com.google.jam.creators;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

public class MultiThreadEnvironmentFunction
        implements Function<Map<Integer, String>, Queue<Entry<Integer, String>>> {

    @Override
    public Queue<Map.Entry<Integer, String>> apply(final Map<Integer, String> data) {
        return new LinkedBlockingQueue<>(data.entrySet());
    }
}
