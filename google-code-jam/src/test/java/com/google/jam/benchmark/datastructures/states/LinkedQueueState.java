package com.google.jam.benchmark.datastructures.states;

import java.util.LinkedList;
import java.util.Queue;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class LinkedQueueState
        extends AbstractQueueState {

    @Override
    protected Queue<Integer> buildQueue(int queueCapacity) {
        return new LinkedList<>();
    }
}
