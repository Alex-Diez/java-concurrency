package org.concurrent.datastructures.benchmark;

import java.util.concurrent.TimeUnit;

import org.concurrent.datastructures.benchmark.states.DataState;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

public class Base {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void lineBurnCPU(final Blackhole blackhole, final DataState dataState)
            throws Exception {
        Blackhole.consumeCPU(QueuesBenchmarks.CPU_TOKENS);
        blackhole.consume(dataState.getDatum());
    }
}
