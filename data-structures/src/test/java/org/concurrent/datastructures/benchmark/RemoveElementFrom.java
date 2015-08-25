package org.concurrent.datastructures.benchmark;

import java.util.concurrent.TimeUnit;

import org.concurrent.datastructures.benchmark.states.ArrayQueueState;
import org.concurrent.datastructures.benchmark.states.DataState;
import org.concurrent.datastructures.benchmark.states.FairArrayBlockingQueueState;
import org.concurrent.datastructures.benchmark.states.LastIndexLinkedBlockingQueueState;
import org.concurrent.datastructures.benchmark.states.LinkedQueueState;
import org.concurrent.datastructures.benchmark.states.UnfairArrayBlockingQueueState;
import org.concurrent.datastructures.benchmark.states.LinkedBlockingQueueState;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

public class RemoveElementFrom {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void ArrayQueue(
            final ArrayQueueState arrayQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        arrayQueueState.getQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LinkedQueue(final LinkedQueueState linkedQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        linkedQueueState.getQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void FairArrayBlockingQueue(
            final FairArrayBlockingQueueState fairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        fairArrayBlockingQueueState.getQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void UnfairArrayBlockingQueue(
            final UnfairArrayBlockingQueueState unfairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        unfairArrayBlockingQueueState.getQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LinkedBlockingQueue(
            final LinkedBlockingQueueState linkedBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        linkedBlockingQueueState.getQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingQueueState lastIndexLinkedBlockingQueueState,
            final DataState dataState,
            final Blackhole blackhole)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        try {
            lastIndexLinkedBlockingQueueState.getQueue().remove(dataState.getDatum());
        }
        catch (UnsupportedOperationException e) {
            blackhole.consume(e);
        }
    }
}
