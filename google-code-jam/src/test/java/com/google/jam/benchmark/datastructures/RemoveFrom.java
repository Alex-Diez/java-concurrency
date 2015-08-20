package com.google.jam.benchmark.datastructures;

import java.util.concurrent.TimeUnit;

import com.google.jam.benchmark.datastructures.states.ArrayBigQueueState;
import com.google.jam.benchmark.datastructures.states.ArrayLargeQueueState;
import com.google.jam.benchmark.datastructures.states.FairArrayBlockingBigQueueState;
import com.google.jam.benchmark.datastructures.states.FairArrayBlockingLargeQueueState;
import com.google.jam.benchmark.datastructures.states.LastIndexLinkedBlockingBigQueueState;
import com.google.jam.benchmark.datastructures.states.LastIndexLinkedBlockingLargeQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedBigQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedBlockingBigQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedBlockingLargeQueueState;
import com.google.jam.benchmark.datastructures.states.LinkedLargeQueueState;
import com.google.jam.benchmark.datastructures.states.UnfairArrayBlockingBigQueueState;
import com.google.jam.benchmark.datastructures.states.UnfairArrayBlockingLargeQueueState;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

import static com.google.jam.benchmark.datastructures.QueuesBenchmarks.CPU_TOKENS;

public class RemoveFrom {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BigArrayQueue(final ArrayBigQueueState arrayBigQueueState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        arrayBigQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BigLinkedQueue(final LinkedBigQueueState linkedBigQueueState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBigQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BigFairArrayBlockingQueue(final FairArrayBlockingBigQueueState fairArrayBlockingBigQueueState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        fairArrayBlockingBigQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BigUnfairArrayBlockingQueue(final UnfairArrayBlockingBigQueueState unfairArrayBlockingBigQueueState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        unfairArrayBlockingBigQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BigLinkedBlockingQueue(final LinkedBlockingBigQueueState linkedBlockingBigQueueState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBlockingBigQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BigLastIndexLinkedBlockingQueue(final LastIndexLinkedBlockingBigQueueState lastIndexLinkedBlockingBigQueueState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        lastIndexLinkedBlockingBigQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeArrayQueue(final ArrayLargeQueueState arrayLargeQueueState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        arrayLargeQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeLinkedQueue(final LinkedLargeQueueState linkedLargeQueueState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedLargeQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeFairArrayBlockingQueue(final FairArrayBlockingLargeQueueState fairArrayBlockingLargeQueueState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        fairArrayBlockingLargeQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeUnfairArrayBlockingQueue(final UnfairArrayBlockingLargeQueueState unfairArrayBlockingLargeQueueState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        unfairArrayBlockingLargeQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeLinkedBlockingQueue(final LinkedBlockingLargeQueueState linkedBlockingLargeQueueState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        linkedBlockingLargeQueueState.getQueue().remove();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void LargeLastIndexLinkedBlockingQueue(final LastIndexLinkedBlockingLargeQueueState lastIndexLinkedBlockingLargeQueueState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        lastIndexLinkedBlockingLargeQueueState.getQueue().remove();
    }
}
