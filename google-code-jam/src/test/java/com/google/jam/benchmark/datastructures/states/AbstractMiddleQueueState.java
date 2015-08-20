package com.google.jam.benchmark.datastructures.states;

public abstract class AbstractMiddleQueueState
        extends AbstractQueueState {

    @Override
    public int startQueueSize() {
        return MIDDLE_QUEUE_SIZE;
    }
}
