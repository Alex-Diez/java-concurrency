package org.concurrent.datastructures.benchmark.states;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class UnfairArrayBlockingQueueState
        extends AbstractQueueState {

    @Override
    public Queue<String> buildQueue(int queueCapacity) {
        return new ArrayBlockingQueue<>(queueCapacity, false);
    }
}
