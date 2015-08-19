package com.google.jam.benchmark.datastructures;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.ArrayBlockingQueue;

@State(Scope.Thread)
public class FairArrayBlockingQueueState
        extends AbstractQueueState {

    static final int ARRAY_BLOCKING_QUEUE_SIZE = 30_000_000;

    @Setup(Level.Trial)
    public void setUp() {
        queue = new ArrayBlockingQueue<>(ARRAY_BLOCKING_QUEUE_SIZE, true);
    }
}
