package com.google.jam.benchmark.datastructures.states;

public abstract class AbstractLargeQueueState
        extends AbstractQueueState {

    @Override
    public int startQueueSize() {
        return LARGE_QUEUE_SIZE;
    }
}
