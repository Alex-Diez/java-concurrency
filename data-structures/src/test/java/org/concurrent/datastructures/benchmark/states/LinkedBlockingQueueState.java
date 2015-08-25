package org.concurrent.datastructures.benchmark.states;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueState
        extends AbstractQueueState {

    @Override
    public Queue<String> buildQueue(int queueCapacity) {
        return new LinkedBlockingQueue<>(queueCapacity);
    }
}
