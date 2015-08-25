package com.google.jam.benchmark.algorithms;

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

public class JMHMain {

    public static final int TOKENS = 1_000_000;

    public static void main(String[] args)
            throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(JMHMain.class.getSimpleName())
                .warmupIterations(50)
                .measurementIterations(50)
                .forks(1)
                .jvmArgs("-ea")
                .build();
        new Runner(options).run();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void multiThreadPerformance(final MultiThread multiThread)
            throws Exception {
        Blackhole.consumeCPU(TOKENS);
        multiThread.results = multiThread.resolver.solve(multiThread.largeRound, multiThread.algorithm);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void singleThreadPerformance(final SingleThread singleThread)
            throws Exception {
        Blackhole.consumeCPU(TOKENS);
        singleThread.results = singleThread.resolver.solve(singleThread.largeRound, singleThread.algorithm);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void baselineBurnCPU()
            throws Exception {
        Blackhole.consumeCPU(TOKENS);
    }
}
