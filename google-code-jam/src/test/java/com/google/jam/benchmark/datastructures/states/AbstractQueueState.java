package com.google.jam.benchmark.datastructures.states;

import java.util.Queue;
import java.util.Random;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

@State(Scope.Thread)
public abstract class AbstractQueueState
        implements QueueState {

    protected final Random random = new Random();

    private Queue<Integer> testedQueue;
    private Queue<Integer> initialQueue;

    @Override
    public Queue<Integer> getQueue() {
        return testedQueue;
    }

    @Override
    @Setup(Level.Trial)
    public void setUp() {
        testedQueue = buildQueue(LARGE_QUEUE_SIZE);
        initialQueue = buildQueue(LARGE_QUEUE_SIZE);
        fillQueue();
    }

    @Override
    @TearDown
    public void checkUp() {
        assert !testedQueue.equals(initialQueue);
        testedQueue = null;
        initialQueue = null;
    }

    @Override
    public void fillQueue() {
        final int length = startQueueSize();
        for (int i = 0; i < length; i++) {
            final int e = random.nextInt();
            testedQueue.add(e);
            initialQueue.add(e);
        }
    }
}
