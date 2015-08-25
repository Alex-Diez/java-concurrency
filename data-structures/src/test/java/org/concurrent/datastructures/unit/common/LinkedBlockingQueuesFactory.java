package org.concurrent.datastructures.unit.common;

import java.util.Arrays;
import java.util.List;

import org.concurrent.datastructures.LastIndexTaskLinkedBlockingQueue;
import org.concurrent.datastructures.LastIndexTaskQueue;

class LinkedBlockingQueuesFactory
        implements QueuesFactory {

    private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

    @Override
    public LastIndexTaskQueue<Integer> buildEmptyQueue() {
        return new LastIndexTaskLinkedBlockingQueue<>();
    }

    @Override
    public LastIndexTaskQueue<Integer> buildFullQueue() {
        return new LastIndexTaskLinkedBlockingQueue<>(DATA);
    }
}
