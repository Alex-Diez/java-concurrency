package com.google.jam.benchmark.datastructures.states;

import java.util.Queue;
import java.util.Random;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import static java.lang.Math.abs;

@State(Scope.Thread)
public abstract class AbstractQueueState {

    static final int LARGE_QUEUE_SIZE = 30_000_000;

    final Random random = new Random();

    Queue<Integer> emptyQueue;
    Queue<Integer> largeQueue;

    public Queue<Integer> getEmptyQueue() {
        return emptyQueue;
    }

    public Queue<Integer> getLargeQueue() {
        return largeQueue;
    }

    @Setup(Level.Trial)
    public void setUp() {
        emptyQueue = buildQueue(LARGE_QUEUE_SIZE);
        largeQueue = buildQueue(LARGE_QUEUE_SIZE);
        for (int i = 0; i < LARGE_QUEUE_SIZE; i++) {
            largeQueue.add(random.nextInt());
        }
    }

    @TearDown(Level.Iteration)
    public void printIterationNumber() {
        if(!emptyQueue.isEmpty()) {
            printNumberOfMethodInvocation(emptyQueue, 0);
        }
        if(largeQueue.size() != LARGE_QUEUE_SIZE) {
            printNumberOfMethodInvocation(largeQueue, LARGE_QUEUE_SIZE);
        }
    }

    @TearDown(Level.Trial)
    public void checkUp() {
        emptyQueue = null;
        largeQueue = null;
    }

    protected abstract Queue<Integer> buildQueue(final int queueCapacity);

    private static void printNumberOfMethodInvocation(final Queue<Integer> queue, final int offset) {
        System.out.printf("%nNumber of iterations is %d%n", abs(offset - queue.size()));
    }
}
