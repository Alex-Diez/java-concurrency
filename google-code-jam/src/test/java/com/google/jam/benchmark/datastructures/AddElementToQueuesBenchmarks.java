package com.google.jam.benchmark.datastructures;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class AddElementToQueuesBenchmarks {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddElementToQueuesBenchmarks.class);

    private static final long CPU_TOKENS = 2000;

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void baseline()
            throws Exception {
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void baselineBurnCPU()
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void addElementToArrayQueue(final ArrayQueueState arrayQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        LOGGER.info("Datum which is going to be added is {}", dataState.datum);
        arrayQueueState.queue.add(dataState.datum);
        dataState.counter++;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void addElementToLinkedQueue(final LinkedQueueState linkedQueueState, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        LOGGER.info("Datum which is going to be added is {}", dataState.datum);
        linkedQueueState.queue.add(dataState.datum);
        dataState.counter++;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void addElementToFairArrayBlockingQueue(
            final FairArrayBlockingQueueState fairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        LOGGER.info("Datum which is going to be added is {}", dataState.datum);
        fairArrayBlockingQueueState.queue.add(dataState.datum);
        dataState.counter++;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void addElementToUnfairArrayBlockingQueue(
            final UnfairArrayBlockingQueueState unfairArrayBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        LOGGER.info("Datum which is going to be added is {}", dataState.datum);
        unfairArrayBlockingQueueState.queue.add(dataState.datum);
        dataState.counter++;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void addElementToLinkedBlockingQueue(
            final LinkedBlockingQueueState linkedBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        LOGGER.info("Datum which is going to be added is {}", dataState.datum);
        linkedBlockingQueueState.queue.add(dataState.datum);
        dataState.counter++;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void addElementToLastIndexLinkedBlockingQueue(
            final LastIndexLinkedBlockingQueueState lastIndexLinkedBlockingQueueState,
            final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        LOGGER.info("Datum which is going to be added is {}", dataState.datum);
        lastIndexLinkedBlockingQueueState.queue.add(dataState.datum);
        dataState.counter++;
    }

    public static void main(String[] args)
            throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(AddElementToQueuesBenchmarks.class.getSimpleName())
                .warmupIterations(30)
                .measurementIterations(30)
                .forks(1)
                .jvmArgs("-ea")
                .build();
        new Runner(options).run();
    }
}
