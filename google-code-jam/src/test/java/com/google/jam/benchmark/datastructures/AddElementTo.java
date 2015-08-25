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

public class AddElementTo {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void ArrayQueue(final ArrayQueueState arrayEmptyQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        arrayEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LinkedQueue(final LinkedQueueState linkedEmptyQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void FairArrayBlockingQueue(
            final FairArrayBlockingQueueState fairArrayBlockingEmptyQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        fairArrayBlockingEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void UnfairArrayBlockingQueue(
            final UnfairArrayBlockingQueueState unfairArrayBlockingEmptyQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        unfairArrayBlockingEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LinkedBlockingQueue(
            final LinkedBlockingQueueState linkedBlockingEmptyQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBlockingEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingQueueState lastIndexLinkedBlockingEmptyQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        lastIndexLinkedBlockingEmptyQueueState.getQueue().add(dataState.getDatum());
    }
}
