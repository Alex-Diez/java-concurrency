package com.google.jam.benchmark.datastructures.states;

import java.util.LinkedList;
import java.util.Queue;

public class LinkedMiddleQueueState
        extends AbstractMiddleQueueState {

    @Override
    public Queue<Integer> buildQueue(int queueCapacity) {
        return new LinkedList<>();
    }
}
