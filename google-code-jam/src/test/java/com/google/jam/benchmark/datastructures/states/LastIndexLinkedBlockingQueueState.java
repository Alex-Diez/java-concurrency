package com.google.jam.benchmark.datastructures.states;

import java.util.Queue;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;

public class LastIndexLinkedBlockingQueueState
        extends AbstractQueueState {

    @Override
    public Queue<String> buildQueue(int queueCapacity) {
        return new LastIndexTaskLinkedBlockingQueue<>();
    }
}
