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

public class AddElementTo {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void ArrayQueue(final ArrayQueueState arrayEmptyQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        arrayEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LinkedQueue(final LinkedQueueState linkedEmptyQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        linkedEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void FairArrayBlockingQueue(
            final FairArrayBlockingQueueState fairArrayBlockingEmptyQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        fairArrayBlockingEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void UnfairArrayBlockingQueue(
            final UnfairArrayBlockingQueueState unfairArrayBlockingEmptyQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        unfairArrayBlockingEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LinkedBlockingQueue(
            final LinkedBlockingQueueState linkedBlockingEmptyQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        linkedBlockingEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingQueueState lastIndexLinkedBlockingEmptyQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        lastIndexLinkedBlockingEmptyQueueState.getQueue().add(dataState.getDatum());
    }
}
