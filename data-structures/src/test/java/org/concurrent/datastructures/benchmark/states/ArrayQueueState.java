package org.concurrent.datastructures.benchmark.states;

import java.util.ArrayDeque;
import java.util.Queue;

public class ArrayQueueState
        extends AbstractQueueState {

    @Override
    public Queue<String> buildQueue(int queueCapacity) {
        return new ArrayDeque<>(queueCapacity);
    }
}
