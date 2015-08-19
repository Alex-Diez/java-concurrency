package com.google.jam.benchmark.datastructures;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayDeque;

import static com.google.jam.benchmark.datastructures.FairArrayBlockingQueueState.ARRAY_BLOCKING_QUEUE_SIZE;

@State(Scope.Thread)
public class ArrayQueueState
        extends AbstractQueueState {

    @Setup(Level.Trial)
    public void setUp() {
        queue = new ArrayDeque<>(ARRAY_BLOCKING_QUEUE_SIZE);
    }
}
