package com.google.jam.benchmark.datastructures;

import java.util.concurrent.TimeUnit;

import com.google.jam.benchmark.datastructures.states.DataState;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

import static com.google.jam.benchmark.datastructures.QueuesBenchmarks.CPU_TOKENS;

public class Base {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void lineBurnCPU(final Blackhole blackhole, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(CPU_TOKENS);
        blackhole.consume(dataState.getDatum());
    }
}
