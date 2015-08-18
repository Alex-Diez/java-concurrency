package com.google.jam.benchmark.standingovation.singlethread;

import com.google.jam.Round;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;
import com.google.jam.algorithms.StandingOvationContestAnalysisAlgorithm;
import com.google.jam.algorithms.StandingOvationForwardCountingAlgorithm;
import com.google.jam.creators.RoundCreator;
import com.google.jam.RoundFunctionFactory;
import com.google.jam.creators.SingleThreadEnvironmentFunction;
import com.google.jam.datastructures.LastIndexTaskQueue;
import com.google.jam.solvers.RoundResolver;
import com.google.jam.solvers.SingleThreadRoundResolver;
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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
public class SingleThreadStandingOvationResolverPerformanceBenchmark {

    @Param({"forward", "StandingOvationContestAnalysis"})
    private String algorithmType;

    private Round largeRound;
    private Round smallRound;
    private RoundResolver resolver;
    private Function<String, Integer> algorithm;
    private Map<Integer, Integer> largeResult;
    private Map<Integer, Integer> smallResult;

    @Setup
    public void setUp()
            throws Exception {
        final RoundCreator creator = new RoundCreator();
        final char roundLetter = 'A';
        final RoundPathBuilder largeTaskPathBuilder = new RoundPathBuilder("main", roundLetter, "large", "practice");
        final Function<List<String>, Collection<String>> roundFunction =
                new RoundFunctionFactory().createRoundFunction(roundLetter);
        final Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction =
                new SingleThreadEnvironmentFunction();
        largeRound = new RoundTaskReader(largeTaskPathBuilder.build()).applyCreator(
                creator,
                roundFunction,
                threadEnvironmentFunction
        );
        final RoundPathBuilder smallTaskPathBuilder = new RoundPathBuilder("main", roundLetter, "small", "practice");
        smallRound = new RoundTaskReader(smallTaskPathBuilder.build()).applyCreator(
                creator,
                roundFunction,
                threadEnvironmentFunction
        );
        resolver = new SingleThreadRoundResolver();
        algorithm = algorithmType.equals("forward")
                ? new StandingOvationForwardCountingAlgorithm()
                : new StandingOvationContestAnalysisAlgorithm();
        if(smallResult != null) {
            smallResult.clear();
        }
        if(largeResult != null) {
            largeResult.clear();
        }
    }

    @TearDown
    public void tearDown()
            throws Exception {
        assert largeResult.size() == 100;
        assert smallResult.size() == 100;
    }

    @Benchmark
    public void performanceOfLargeTaskSolvingProcess()
            throws Exception {
        largeResult = resolver.solve(largeRound, algorithm);
    }

    @Benchmark
    public void performanceOfSmallTaskSolvingProcess()
            throws Exception {
        smallResult = resolver.solve(smallRound, algorithm);
    }
}
