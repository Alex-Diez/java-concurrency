package com.google.jam.benchmark.datastructures.states;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Queue;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

@State(Scope.Thread)
public abstract class AbstractQueueState {

    int LARGE_QUEUE_CAPACITY = 400_000;
    int LARGE_QUEUE_SIZE = LARGE_QUEUE_CAPACITY / 5 * 2;
    int BIG_QUEUE_SIZE = LARGE_QUEUE_SIZE / 10;
    int MIDDLE_QUEUE_SIZE = BIG_QUEUE_SIZE / 10;
    int SMALL_QUEUE_SIZE = MIDDLE_QUEUE_SIZE / 10;
    int EMPTY_QUEUE_SIZE = 0;

    @Param({"EMPTY", "SMALL", "MIDDLE", "BIG", "LARGE"})
    String size;

    protected final SecureRandom random = new SecureRandom();

    private Queue<String> testedQueue;
    private Queue<String> initialQueue;

    public Queue<String> getQueue() {
        return testedQueue;
    }

    public int startQueueSize() {
        switch (size) {
            case "EMPTY":
                return EMPTY_QUEUE_SIZE;
            case "SMALL":
                return SMALL_QUEUE_SIZE;
            case "MIDDLE":
                return MIDDLE_QUEUE_SIZE;
            case "BIG":
                return BIG_QUEUE_SIZE;
            case "LARGE":
                return LARGE_QUEUE_SIZE;
        }
        throw new RuntimeException("Impossible situation");
    }

    protected abstract Queue<String> buildQueue(final int queueCapacity);

    @Setup(Level.Trial)
    public void setUp() {
        testedQueue = buildQueue(LARGE_QUEUE_CAPACITY);
        initialQueue = buildQueue(LARGE_QUEUE_CAPACITY);
        fillQueue();
    }

    @TearDown
    public void checkUp() {
        assert !testedQueue.equals(initialQueue);
        testedQueue = null;
        initialQueue = null;
    }

    public void fillQueue() {
        final int length = startQueueSize();
        for (int i = 0; i < length; i++) {
            final String e = new BigInteger(120, random).toString();
            testedQueue.add(e);
            initialQueue.add(e);
        }
    }
}
