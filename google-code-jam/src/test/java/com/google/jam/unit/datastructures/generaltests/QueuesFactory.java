package com.google.jam.unit.datastructures.generaltests;

import com.google.jam.datastructures.LastIndexTaskQueue;

interface QueuesFactory {

    LastIndexTaskQueue<Integer> buildEmptyQueue();

    LastIndexTaskQueue<Integer> buildFullQueue();
}
