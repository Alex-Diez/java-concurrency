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
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void ArrayQueue(
            final ArrayQueueState arrayQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        arrayQueueState.getQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LinkedQueue(final LinkedQueueState linkedQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedQueueState.getQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void FairArrayBlockingQueue(
            final FairArrayBlockingQueueState fairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        fairArrayBlockingQueueState.getQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void UnfairArrayBlockingQueue(
            final UnfairArrayBlockingQueueState unfairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        unfairArrayBlockingQueueState.getQueue().remove(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LinkedBlockingQueue(
            final LinkedBlockingQueueState linkedBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
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
        Blackhole.consumeCPU(CPU_TOKENS);
        try {
            lastIndexLinkedBlockingQueueState.getQueue().remove(dataState.getDatum());
        }
        catch (UnsupportedOperationException e) {
            blackhole.consume(e);
        }
    }
}
