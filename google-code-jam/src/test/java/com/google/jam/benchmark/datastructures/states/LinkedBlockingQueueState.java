package com.google.jam.benchmark.datastructures.states;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@State(Scope.Thread)
public class LinkedBlockingQueueState
        extends AbstractQueueState {

    @Override
    protected Queue<Integer> buildQueue(int queueCapacity) {
        return new LinkedBlockingQueue<>(queueCapacity);
    }
}
