package com.google.jam.benchmark.datastructures.states;

public abstract class AbstractEmptyQueueState
        extends AbstractQueueState {

    @Override
    public int startQueueSize() {
        return EMPTY_QUEUE_SIZE;
    }
}
