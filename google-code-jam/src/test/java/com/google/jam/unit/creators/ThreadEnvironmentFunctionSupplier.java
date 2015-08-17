package com.google.jam.unit.creators;

import com.google.jam.creators.MultiThreadEnvironmentFunction;
import com.google.jam.creators.SingleThreadEnvironmentFunction;
import com.google.jam.datastructures.LastIndexTaskQueue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

class ThreadEnvironmentFunctionSupplier
        implements Supplier<Iterator<Function<Collection<String>, LastIndexTaskQueue<String>>>> {

    @Override
    public Iterator<Function<Collection<String>, LastIndexTaskQueue<String>>> get() {
        return Arrays.asList(new SingleThreadEnvironmentFunction(), new MultiThreadEnvironmentFunction()).iterator();
    }
}
