package com.google.jam.benchmark.datastructures.states;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingLargeQueueState
        extends AbstractLargeQueueState {

    @Override
    public Queue<Integer> buildQueue(int queueCapacity) {
        return new LinkedBlockingQueue<>(queueCapacity);
    }
}
