package com.google.jam.benchmark.datastructures;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.util.ArrayDeque;
import java.util.Queue;

@State(Scope.Benchmark)
public class ArrayQueueState {

    Queue<Integer> queue;

    @Setup
    public void setUp() {
        queue = new ArrayDeque<>();
    }

    @TearDown
    public void tearDown()
            throws Exception {
        assert queue.size() == 1;
    }
}
