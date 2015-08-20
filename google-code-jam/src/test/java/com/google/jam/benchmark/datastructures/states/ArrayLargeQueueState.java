package com.google.jam.benchmark.datastructures.states;

import java.util.ArrayDeque;
import java.util.Queue;

public class ArrayLargeQueueState
        extends AbstractLargeQueueState {

    @Override
    public Queue<Integer> buildQueue(int queueCapacity) {
        return new ArrayDeque<>(queueCapacity);
    }
}
