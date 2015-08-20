package com.google.jam.benchmark.datastructures.states;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingEmptyQueueState
        extends AbstractEmptyQueueState {

    @Override
    public Queue<Integer> buildQueue(int queueCapacity) {
        return new LinkedBlockingQueue<>(queueCapacity);
    }
}
