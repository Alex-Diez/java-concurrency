package com.google.jam.benchmark.datastructures.states;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class UnfairArrayBlockingMiddleQueueState
        extends AbstractMiddleQueueState {

    @Override
    public Queue<Integer> buildQueue(int queueCapacity) {
        return new ArrayBlockingQueue<>(queueCapacity, false);
    }
}
