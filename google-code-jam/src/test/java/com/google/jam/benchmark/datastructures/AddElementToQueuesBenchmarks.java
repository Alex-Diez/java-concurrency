package com.google.jam.benchmark.datastructures;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

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

public class AddElementToQueuesBenchmarks {

    public static final Collection<Integer> DATA = Arrays.asList(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
            31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
            51, 52, 53, 54, 55, 56, 57, 85, 95, 60,
            61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
            71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
            81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
            91, 92, 93, 94, 95, 96, 97, 98, 99, 100
    );

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void baseline()
            throws Exception {
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void addElementToArrayQueue(ArrayQueueState arrayQueueState)
            throws Exception {
        Blackhole.consumeCPU(arrayQueueState.cpu);
        arrayQueueState.queue.add(1);
        arrayQueueState.counter++;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void addElementToLinkedQueue(LinkedQueueState linkedQueueState)
            throws Exception {
        Blackhole.consumeCPU(linkedQueueState.cpu);
        linkedQueueState.queue.add(1);
        linkedQueueState.counter++;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void addElementToHashMap(HashMapState hashMapState)
            throws Exception {
        Blackhole.consumeCPU(hashMapState.cpu);
        hashMapState.map.put("1", "1");
        hashMapState.counter++;
    }

    public static void main(String[] args)
            throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(AddElementToQueuesBenchmarks.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .jvmArgs("-ea")
                .build();
        new Runner(options).run();
    }
}
