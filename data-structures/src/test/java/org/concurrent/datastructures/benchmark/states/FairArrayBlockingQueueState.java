package org.concurrent.datastructures.benchmark.states;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class FairArrayBlockingQueueState
        extends AbstractQueueState {

    @Override
    public Queue<String> buildQueue(int queueCapacity) {
        return new ArrayBlockingQueue<>(queueCapacity, true);
    }
}
