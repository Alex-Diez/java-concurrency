package com.google.jam.benchmark.datastructures.states;

import java.util.Queue;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public interface QueueState {

    int LARGE_QUEUE_SIZE = 40_000_000;
    int BIG_QUEUE_SIZE = 400_000;
    int MIDDLE_QUEUE_SIZE = 4000;
    int SMALL_QUEUE_SIZE = 40;
    int EMPTY_QUEUE_SIZE = 0;

    Queue<Integer> getQueue();

    Queue<Integer> buildQueue(final int queueCapacity);

    void setUp();

    void checkUp();

    int startQueueSize();

    void fillQueue();
}
