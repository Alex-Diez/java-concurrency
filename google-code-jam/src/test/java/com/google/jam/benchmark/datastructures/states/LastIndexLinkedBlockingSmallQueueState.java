package com.google.jam.benchmark.datastructures.states;

import java.util.Queue;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;

public class LastIndexLinkedBlockingSmallQueueState
        extends AbstractSmallQueueState {

    @Override
    public Queue<Integer> buildQueue(int queueCapacity) {
        return new LastIndexTaskLinkedBlockingQueue<>();
    }
}
