package org.concurrent.datastructures.unit.common;

import java.util.Arrays;
import java.util.List;

import org.concurrent.datastructures.LastIndexTaskLinkedQueue;
import org.concurrent.datastructures.LastIndexTaskQueue;

class LinkedQueuesFactory
        implements QueuesFactory {

    private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

    @Override
    public LastIndexTaskQueue<Integer> buildEmptyQueue() {
        return new LastIndexTaskLinkedQueue<>();
    }

    @Override
    public LastIndexTaskQueue<Integer> buildFullQueue() {
        return new LastIndexTaskLinkedQueue<>(DATA);
    }
}
