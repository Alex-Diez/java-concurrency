package com.google.jam.benchmark.datastructures;

import java.util.ArrayDeque;
import java.util.Queue;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@State(Scope.Thread)
public class ArrayQueueState {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayQueueState.class);

    @Param("2000")
    int cpu;

    Queue<Integer> queue;
    int counter;

    @Setup(Level.Trial)
    public void setUp() {
        LOGGER.info("Initialize state");
        queue = new ArrayDeque<>();
        counter = 0;
    }

    @TearDown(Level.Trial)
    public void tearDown()
            throws Exception {
        LOGGER.info("Let check it up!");
        LOGGER.info("ArrayQueueState was called {} times", counter);
    }
}
