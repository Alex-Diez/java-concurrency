package com.google.jam.benchmark.datastructures.states;

import java.util.Queue;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;

public class LastIndexLinkedBlockingEmptyQueueState
        extends AbstractEmptyQueueState {

    @Override
    public Queue<Integer> buildQueue(int queueCapacity) {
        return new LastIndexTaskLinkedBlockingQueue<>();
    }
}
