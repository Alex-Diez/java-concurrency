package com.google.jam.benchmark.datastructures.states;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class UnfairArrayBlockingEmptyQueueState
        extends AbstractEmptyQueueState {

    @Override
    public Queue<Integer> buildQueue(int queueCapacity) {
        return new ArrayBlockingQueue<>(queueCapacity, false);
    }
}
