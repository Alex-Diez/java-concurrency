package com.google.jam.unit.datastructures.common;

import com.google.jam.datastructures.LastIndexTaskQueue;

interface QueuesFactory {

    LastIndexTaskQueue<Integer> buildEmptyQueue();

    LastIndexTaskQueue<Integer> buildFullQueue();
}
