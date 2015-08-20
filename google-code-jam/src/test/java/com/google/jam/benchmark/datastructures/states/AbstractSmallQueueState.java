package com.google.jam.benchmark.datastructures.states;

import java.util.Queue;

public abstract class AbstractSmallQueueState
        extends AbstractQueueState {

    @Override
    public int startQueueSize() {
        return SMALL_QUEUE_SIZE / 3 * 2;
    }
}
