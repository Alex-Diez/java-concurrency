package com.google.jam.unit.datastructures.common;

import java.util.Arrays;
import java.util.List;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

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
