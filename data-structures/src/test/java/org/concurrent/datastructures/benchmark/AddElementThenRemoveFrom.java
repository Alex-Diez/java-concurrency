package org.concurrent.datastructures.benchmark;

import java.util.concurrent.TimeUnit;

import org.concurrent.datastructures.benchmark.states.DataState;
import org.concurrent.datastructures.benchmark.states.FairArrayBlockingQueueState;
import org.concurrent.datastructures.benchmark.states.LinkedQueueState;
import org.concurrent.datastructures.benchmark.states.ArrayQueueState;
import org.concurrent.datastructures.benchmark.states.LastIndexLinkedBlockingQueueState;
import org.concurrent.datastructures.benchmark.states.LinkedBlockingQueueState;
import org.concurrent.datastructures.benchmark.states.UnfairArrayBlockingQueueState;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

public class AddElementThenRemoveFrom {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void ArrayQueue(final ArrayQueueState arrayQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        arrayQueueState.getQueue().add(dataState.getDatum());
        arrayQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LinkedQueue(final LinkedQueueState linkedQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        linkedQueueState.getQueue().add(dataState.getDatum());
        linkedQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void FairArrayBlockingQueue(
            final FairArrayBlockingQueueState fairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        fairArrayBlockingQueueState.getQueue().add(dataState.getDatum());
        fairArrayBlockingQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void UnfairArrayBlockingQueue(
            final UnfairArrayBlockingQueueState unfairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        unfairArrayBlockingQueueState.getQueue().add(dataState.getDatum());
        unfairArrayBlockingQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LinkedBlockingQueue(
            final LinkedBlockingQueueState linkedBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        linkedBlockingQueueState.getQueue().add(dataState.getDatum());
        linkedBlockingQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void LastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingQueueState lastIndexLinkedBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        lastIndexLinkedBlockingQueueState.getQueue().add(dataState.getDatum());
        lastIndexLinkedBlockingQueueState.getQueue().remove();
    }
}
