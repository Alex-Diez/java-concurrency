package com.google.jam.benchmark.datastructures;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.util.Queue;

@State(Scope.Thread)
public abstract class AbstractQueueState {

    Queue<Integer> queue;

    @TearDown(Level.Trial)
    public void checkUp()
            throws Exception {
        assert !queue.isEmpty();
        queue = null;
    }
}
