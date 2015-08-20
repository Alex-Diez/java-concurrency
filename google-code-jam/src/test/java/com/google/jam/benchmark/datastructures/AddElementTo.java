package com.google.jam.benchmark.datastructures;

import java.util.concurrent.TimeUnit;

import com.google.jam.benchmark.datastructures.states.ArrayBigQueueState;
import com.google.jam.benchmark.datastructures.states.ArrayEmptyQueueState;
import com.google.jam.benchmark.datastructures.states.ArrayLargeQueueState;
import com.google.jam.benchmark.datastructures.states.ArrayMiddleQueueState;
import com.google.jam.benchmark.datastructures.states.ArraySmallQueueState;
import com.google.jam.benchmark.datastructures.states.DataState;
import com.google.jam.benchmark.datastructures.states.FairArrayBlockingBigQueueState;
import com.google.jam.benchmark.datastructures.states.FairArrayBlockingEmptyQueueState;
import com.google.jam.benchmark.datastructures.states.FairArrayBlockingLargeQueueState;
import com.google.jam.benchmark.datastructures.states.FairArrayBlockingMiddleQueueState;
import com.google.jam.benchmark.datastructures.states.FairArrayBlockingSmallQueueState;
import com.google.jam.benchmark.datastructures.states.LastIndexLinkedBlockingBigQueueState;
import com.google.jam.benchmark.datastructures.states.LastIndexLinkedBlockingEmptyQueueState;
import com.google.jam.benchmark.datastructures.states.LastIndexLinkedBlockingLargeQueueState;
import com.google.jam.benchmark.datastructures.states.LastIndexLinkedBlockingMiddleQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedBigQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedBlockingBigQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedBlockingEmptyQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedBlockingLargeQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedBlockingMiddleQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedBlockingSmallQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedEmptyQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedLargeQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedMiddleQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedSmallQueueState;
import com.google.jam.benchmark.datastructures.states.UnfairArrayBlockingBigQueueState;
import com.google.jam.benchmark.datastructures.states.UnfairArrayBlockingEmptyQueueState;
import com.google.jam.benchmark.datastructures.states.UnfairArrayBlockingLargeQueueState;
import com.google.jam.benchmark.datastructures.states.UnfairArrayBlockingMiddleQueueState;
import com.google.jam.benchmark.datastructures.states.UnfairArrayBlockingSmallQueueState;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

import static com.google.jam.benchmark.datastructures.QueuesBenchmarks.CPU_TOKENS;

public class AddElementTo {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void EmptyArrayQueue(final ArrayEmptyQueueState arrayEmptyQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        arrayEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void EmptyLinkedQueue(final LinkedEmptyQueueState linkedEmptyQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void EmptyFairArrayBlockingQueue(
            final FairArrayBlockingEmptyQueueState fairArrayBlockingEmptyQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        fairArrayBlockingEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void EmptyUnfairArrayBlockingQueue(
            final UnfairArrayBlockingEmptyQueueState unfairArrayBlockingEmptyQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        unfairArrayBlockingEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void EmptyLinkedBlockingQueue(
            final LinkedBlockingEmptyQueueState linkedBlockingEmptyQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBlockingEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void EmptyLastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingEmptyQueueState lastIndexLinkedBlockingEmptyQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        lastIndexLinkedBlockingEmptyQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void SmallArrayQueue(final ArraySmallQueueState arraySmallQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        arraySmallQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void SmallLinkedQueue(final LinkedSmallQueueState linkedSmallQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedSmallQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void SmallFairArrayBlockingQueue(
            final FairArrayBlockingSmallQueueState fairArrayBlockingSmallQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        fairArrayBlockingSmallQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void SmallUnfairArrayBlockingQueue(
            final UnfairArrayBlockingSmallQueueState unfairArrayBlockingSmallQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        unfairArrayBlockingSmallQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void SmallLinkedBlockingQueue(
            final LinkedBlockingSmallQueueState linkedBlockingSmallQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBlockingSmallQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void SmallLastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingEmptyQueueState lastIndexLinkedBlockingSmallQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        lastIndexLinkedBlockingSmallQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void MiddleArrayQueue(final ArrayMiddleQueueState arrayMiddleQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        arrayMiddleQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void MiddleLinkedQueue(final LinkedMiddleQueueState linkedMiddleQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedMiddleQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void MiddleFairArrayBlockingQueue(
            final FairArrayBlockingMiddleQueueState fairArrayBlockingMiddleQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        fairArrayBlockingMiddleQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void MiddleUnfairArrayBlockingQueue(
            final UnfairArrayBlockingMiddleQueueState unfairArrayBlockingMiddleQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        unfairArrayBlockingMiddleQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void MiddleLinkedBlockingQueue(
            final LinkedBlockingMiddleQueueState linkedBlockingMiddleQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBlockingMiddleQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void MiddleLastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingMiddleQueueState lastIndexLinkedBlockingMiddleQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        lastIndexLinkedBlockingMiddleQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BigArrayQueue(final ArrayBigQueueState arrayBigQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        arrayBigQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BigLinkedQueue(final LinkedBigQueueState linkedBigQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBigQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BigFairArrayBlockingQueue(
            final FairArrayBlockingBigQueueState fairArrayBlockingBigQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        fairArrayBlockingBigQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BigUnfairArrayBlockingQueue(
            final UnfairArrayBlockingBigQueueState unfairArrayBlockingBigQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        unfairArrayBlockingBigQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BigLinkedBlockingQueue(
            final LinkedBlockingBigQueueState linkedBlockingBigQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBlockingBigQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BigLastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingBigQueueState lastIndexLinkedBlockingBigQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        lastIndexLinkedBlockingBigQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeArrayQueue(final ArrayLargeQueueState arrayLargeQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        arrayLargeQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeLinkedQueue(final LinkedLargeQueueState linkedLargeQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedLargeQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeFairArrayBlockingQueue(
            final FairArrayBlockingLargeQueueState fairArrayBlockingLargeQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        fairArrayBlockingLargeQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeUnfairArrayBlockingQueue(
            final UnfairArrayBlockingLargeQueueState unfairArrayBlockingLargeQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        unfairArrayBlockingLargeQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeLinkedBlockingQueue(
            final LinkedBlockingLargeQueueState linkedBlockingLargeQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBlockingLargeQueueState.getQueue().add(dataState.getDatum());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeLastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingLargeQueueState lastIndexLinkedBlockingLargeQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        lastIndexLinkedBlockingLargeQueueState.getQueue().add(dataState.getDatum());
    }
}
