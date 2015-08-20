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

public class RemoveElementFrom {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeArrayQueue(
            final ArrayQueueState arrayQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        arrayQueueState.getLargeQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeLinkedQueue(final LinkedQueueState linkedQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedQueueState.getLargeQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeFairArrayBlockingQueue(
            final FairArrayBlockingQueueState fairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        fairArrayBlockingQueueState.getLargeQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeUnfairArrayBlockingQueue(
            final UnfairArrayBlockingQueueState unfairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        unfairArrayBlockingQueueState.getLargeQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeLinkedBlockingQueue(
            final LinkedBlockingQueueState linkedBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBlockingQueueState.getLargeQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeLastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingQueueState lastIndexLinkedBlockingQueueState,
            final DataState dataState,
            final Blackhole blackhole)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        try {
            lastIndexLinkedBlockingQueueState.getLargeQueue().remove(dataState.getDatum());
        }
        catch (UnsupportedOperationException e) {
            blackhole.consume(e);
        }
    }
}
