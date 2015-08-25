package org.concurrent.datastructures.unit.common;

import org.concurrent.datastructures.LastIndexTaskQueue;

interface QueuesFactory {

    LastIndexTaskQueue<Integer> buildEmptyQueue();

    LastIndexTaskQueue<Integer> buildFullQueue();
}
