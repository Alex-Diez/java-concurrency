package com.google.jam.benchmark.datastructures;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class LastIndexLinkedBlockingQueueState
        extends AbstractQueueState {

    @Setup(Level.Trial)
    public void setUp() {
        queue = new LastIndexTaskLinkedBlockingQueue<>();
    }
}
