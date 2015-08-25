package org.concurrent.datastructures.benchmark.states;

import java.util.Queue;

import org.concurrent.datastructures.LastIndexTaskLinkedBlockingQueue;

public class LastIndexLinkedBlockingQueueState
        extends AbstractQueueState {

    @Override
    public Queue<String> buildQueue(int queueCapacity) {
        return new LastIndexTaskLinkedBlockingQueue<>();
    }
}
