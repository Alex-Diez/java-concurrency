package com.google.jam.benchmark.datastructures;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.LinkedList;

@State(Scope.Thread)
public class LinkedQueueState
        extends AbstractQueueState {

    @Setup(Level.Trial)
    public void setUp()
            throws Exception {
        queue = new LinkedList<>();
    }
}
