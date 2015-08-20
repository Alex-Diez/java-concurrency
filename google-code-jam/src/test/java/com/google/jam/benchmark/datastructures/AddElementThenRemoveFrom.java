package com.google.jam.benchmark.datastructures;

import java.util.concurrent.TimeUnit;

import com.google.jam.benchmark.datastructures.states.ArrayQueueState;
import com.google.jam.benchmark.datastructures.states.DataState;
import com.google.jam.benchmark.datastructures.states.FairArrayBlockingQueueState;
import com.google.jam.benchmark.datastructures.states.LastIndexLinkedBlockingQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedBlockingQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedQueueState;
import com.google.jam.benchmark.datastructures.states.UnfairArrayBlockingQueueState;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

import static com.google.jam.benchmark.datastructures.QueuesBenchmarks.CPU_TOKENS;

public class AddElementThenRemoveFrom {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void EmptyArrayQueue(final ArrayQueueState arrayQueueState, final DataState dataState, final Blackhole blackhole)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        arrayQueueState.getEmptyQueue().add(dataState.getDatum());
        arrayQueueState.getEmptyQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void EmptyLinkedQueue(final LinkedQueueState linkedQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedQueueState.getEmptyQueue().add(dataState.getDatum());
        linkedQueueState.getEmptyQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void EmptyFairArrayBlockingQueue(
            final FairArrayBlockingQueueState fairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        fairArrayBlockingQueueState.getEmptyQueue().add(dataState.getDatum());
        fairArrayBlockingQueueState.getEmptyQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void EmptyUnfairArrayBlockingQueue(
            final UnfairArrayBlockingQueueState unfairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        unfairArrayBlockingQueueState.getEmptyQueue().add(dataState.getDatum());
        unfairArrayBlockingQueueState.getEmptyQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void EmptyLinkedBlockingQueue(
            final LinkedBlockingQueueState linkedBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBlockingQueueState.getEmptyQueue().add(dataState.getDatum());
        linkedBlockingQueueState.getEmptyQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void EmptyLastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingQueueState lastIndexLinkedBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        lastIndexLinkedBlockingQueueState.getEmptyQueue().add(dataState.getDatum());
        lastIndexLinkedBlockingQueueState.getEmptyQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeArrayQueue(final ArrayQueueState arrayQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        arrayQueueState.getLargeQueue().add(dataState.getDatum());
        arrayQueueState.getLargeQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeLinkedQueue(final LinkedQueueState linkedQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedQueueState.getLargeQueue().add(dataState.getDatum());
        linkedQueueState.getLargeQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeFairArrayBlockingQueue(
            final FairArrayBlockingQueueState fairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        fairArrayBlockingQueueState.getLargeQueue().add(dataState.getDatum());
        fairArrayBlockingQueueState.getLargeQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeUnfairArrayBlockingQueue(
            final UnfairArrayBlockingQueueState unfairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        unfairArrayBlockingQueueState.getLargeQueue().add(dataState.getDatum());
        unfairArrayBlockingQueueState.getLargeQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeLinkedBlockingQueue(
            final LinkedBlockingQueueState linkedBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBlockingQueueState.getLargeQueue().add(dataState.getDatum());
        linkedBlockingQueueState.getLargeQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeLastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingQueueState lastIndexLinkedBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        lastIndexLinkedBlockingQueueState.getLargeQueue().add(dataState.getDatum());
        lastIndexLinkedBlockingQueueState.getLargeQueue().remove();
    }
}
