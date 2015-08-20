package com.google.jam.benchmark.datastructures.states;

import java.util.LinkedList;
import java.util.Queue;

public class LinkedLargeQueueState
        extends AbstractLargeQueueState {

    @Override
    public Queue<Integer> buildQueue(int queueCapacity) {
        return new LinkedList<>();
    }
}
