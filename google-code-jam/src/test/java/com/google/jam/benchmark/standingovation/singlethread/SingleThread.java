package com.google.jam.benchmark.standingovation.singlethread;

import com.google.jam.Round;
import com.google.jam.RoundFunctionFactory;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;
import com.google.jam.algorithms.StandingOvationContestAnalysisAlgorithm;
import com.google.jam.creators.RoundCreator;
import com.google.jam.solvers.RoundResolver;
import com.google.jam.solvers.SingleThreadRoundResolver;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SingleThread {

    private Round largeRound;
    private RoundResolver resolver;
    private Function<String, Integer> algorithm;

    @Setup
    public void setUp()
            throws Exception {
        final char roundLetter = 'A';
        final RoundPathBuilder largeTaskPathBuilder = new RoundPathBuilder("main", roundLetter, "large", "practice");
        final Function<List<String>, Collection<String>> roundFunction =
                new RoundFunctionFactory().createRoundFunction(roundLetter);
        final RoundCreator creator = new RoundCreator.Builder()
                .setRoundFunction(roundFunction)
                .build();
        largeRound = new RoundTaskReader(largeTaskPathBuilder.build()).applyCreator(creator);
        final RoundPathBuilder smallTaskPathBuilder = new RoundPathBuilder("main", roundLetter, "small", "practice");
        resolver = new SingleThreadRoundResolver();
        algorithm = new StandingOvationContestAnalysisAlgorithm();
    }

    @Benchmark
    public void performanceOfLargeTaskSolvingProcess(final Blackhole blackhole)
            throws Exception {
        blackhole.consume(resolver.solve(largeRound, algorithm));
    }
}
