package org.concurrent.datastructures.benchmark.states;

import java.util.LinkedList;
import java.util.Queue;

public class LinkedQueueState
        extends AbstractQueueState {

    @Override
    public Queue<String> buildQueue(int queueCapacity) {
        return new LinkedList<>();
    }
}
