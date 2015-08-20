package com.google.jam.benchmark.datastructures.states;

import java.util.Queue;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;

public class LastIndexLinkedBlockingMiddleQueueState
        extends AbstractMiddleQueueState {

    @Override
    public Queue<Integer> buildQueue(int queueCapacity) {
        return new LastIndexTaskLinkedBlockingQueue<>();
    }
}
