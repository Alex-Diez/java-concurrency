package com.google.jam.benchmark.datastructures.states;

public abstract class AbstractBigQueueState
        extends AbstractQueueState {

    @Override
    public int startQueueSize() {
        return BIG_QUEUE_SIZE;
    }
}
