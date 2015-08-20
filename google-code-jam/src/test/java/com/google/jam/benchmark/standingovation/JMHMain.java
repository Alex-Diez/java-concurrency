package com.google.jam.benchmark.standingovation;

import com.google.jam.benchmark.standingovation.multithread.MultiThread;
import com.google.jam.benchmark.standingovation.singlethread.SingleThread;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class JMHMain {

    public static void main(String[] args)
            throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(JMHMain.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .jvmArgs("-ea")
                .build();
        new Runner(options).run();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void multiThreadPerformance(final MultiThread multiThread)
            throws Exception {
        Blackhole.consumeCPU(2000);
        multiThread.results = multiThread.resolver.solve(multiThread.largeRound, multiThread.algorithm);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void singleThreadPerformance(final SingleThread singleThread)
            throws Exception {
        Blackhole.consumeCPU(2000);
        singleThread.results = singleThread.resolver.solve(singleThread.largeRound, singleThread.algorithm);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void baselineBurnCPU()
            throws Exception {
        Blackhole.consumeCPU(2000);
    }
}
