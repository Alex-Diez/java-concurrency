package com.google.jam.benchmark.datastructures;

import java.util.concurrent.BlockingQueue;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class LinkedBlockingQueueState {

    private BlockingQueue<Integer> queue;
}
