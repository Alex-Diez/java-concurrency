package com.google.jam.unit.creators;

import com.google.jam.creators.MultiThreadEnvironmentFunction;
import com.google.jam.creators.SingleThreadEnvironmentFunction;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;

class ThreadEnvironmentFunctionSupplier
        implements Supplier<Iterator<Function<Map<Integer, String>, Queue<Entry<Integer, String>>>>> {

    @Override
    public Iterator<Function<Map<Integer, String>, Queue<Entry<Integer, String>>>> get() {
        return Arrays.asList(new SingleThreadEnvironmentFunction(), new MultiThreadEnvironmentFunction()).iterator();
    }
}
