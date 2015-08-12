package com.google.jam.creators;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;

public class SingleThreadEnvironmentFunction
        implements Function<Map<Integer, String>, Queue<Entry<Integer, String>>> {

    @Override
    public Queue<Map.Entry<Integer, String>> apply(final Map<Integer, String> data) {
        return new LinkedList<>(data.entrySet());
    }
}
