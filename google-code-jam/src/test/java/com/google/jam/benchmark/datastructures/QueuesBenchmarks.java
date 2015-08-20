package com.google.jam.benchmark.datastructures;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class QueuesBenchmarks {

    static final long CPU_TOKENS = 2000;

    public static void main(String[] args)
            throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(AddElementTo.class.getSimpleName())
                .include(Base.class.getSimpleName())
                .include(RemoveFrom.class.getSimpleName())
                .warmupIterations(30)
                .measurementIterations(30)
                .forks(1)
                .jvmArgs("-ea")
                .build();
        new Runner(options).run();
    }
}
