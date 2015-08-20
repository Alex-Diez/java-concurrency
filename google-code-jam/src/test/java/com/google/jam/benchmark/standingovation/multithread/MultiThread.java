package com.google.jam.benchmark.standingovation.multithread;

import com.google.jam.Round;
import com.google.jam.RoundFunctionFactory;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;
import com.google.jam.algorithms.StandingOvationContestAnalysisAlgorithm;
import com.google.jam.algorithms.StandingOvationForwardCountingAlgorithm;
import com.google.jam.creators.RoundCreator;
import com.google.jam.experiments.CPUNumberOfThreadFunction;
import com.google.jam.experiments.DoubleCPUNumberOfThreadFunction;
import com.google.jam.solvers.MultiThreadRoundResolver;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class MultiThread {

    @Param({"Double", "Current"})
    private String numberOfThreadFunctionType;

    private MultiThreadRoundResolver resolver;
    private Round largeRound;
    private Function<String, Integer> algorithm;

    @Setup
    public void setUp()
            throws Exception {
        final char roundLetter = 'A';
        final RoundPathBuilder pathBuilder = new RoundPathBuilder("main", roundLetter, "large", "practice");
        final Function<List<String>, Collection<String>> roundFunction =
                new RoundFunctionFactory().createRoundFunction(roundLetter);
        final RoundCreator creator = new RoundCreator.Builder()
                .setRoundFunction(roundFunction)
                .build();
        largeRound = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
        Supplier<Integer> numberOfThreadFunction = numberOfThreadFunctionType.equals("Double")
                ? new DoubleCPUNumberOfThreadFunction()
                : new CPUNumberOfThreadFunction();
        resolver = new MultiThreadRoundResolver(numberOfThreadFunction);
        algorithm = new StandingOvationContestAnalysisAlgorithm();
    }

    @TearDown
    public void tearDown()
            throws Exception {
        resolver.shutDownResolver();
    }

    @Benchmark
    public void performanceOfTaskSolvingProcess(final Blackhole blackhole)
            throws Exception {
        blackhole.consume(resolver.solve(largeRound, algorithm));
    }
}
