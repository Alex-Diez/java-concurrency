package com.google.jam.benchmark.datastructures.states;

import java.util.ArrayDeque;
import java.util.Queue;

public class ArrayMiddleQueueState
        extends AbstractMiddleQueueState {

    @Override
    public Queue<Integer> buildQueue(int queueCapacity) {
        return new ArrayDeque<>(queueCapacity);
    }
}
