package com.google.jam.benchmark.standingovation.multithread;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.jam.Round;
import com.google.jam.creators.RoundCreator;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;
import com.google.jam.algorithms.StandingOvationForwardCountingAlgorithm;
import com.google.jam.algorithms.StandingOvationContestAnalysisAlgorithm;
import com.google.jam.creators.StandingOvationRoundCreator;
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

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
public class MultiThreadStandingOvationResolverPerformanceBenchmark {

    @Param({"forward", "StandingOvationContestAnalysis"})
    private String algorithmType;
    @Param({"Double", "Current"})
    private String numberOfThreadFunctionType;

    private MultiThreadRoundResolver resolver;
    private Round largeRound;
    private Round smallRound;
    private Function<String, Integer> algorithm;
    private Map<Integer, Integer> largeResult;
    private Map<Integer, Integer> smallResult;

    @Setup
    public void setUp()
            throws Exception {
        final RoundCreator creator = new StandingOvationRoundCreator();
        final RoundPathBuilder pathBuilder = new RoundPathBuilder("main", 'A', "large", "practice");
        largeRound = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
        final RoundPathBuilder smallTaskPathBuilder = new RoundPathBuilder("main", 'A', "small", "practice");
        smallRound = new RoundTaskReader(smallTaskPathBuilder.build()).applyCreator(creator);
        Supplier<Integer> numberOfThreadFunction = numberOfThreadFunctionType.equals("Double")
                ? new DoubleCPUNumberOfThreadFunction()
                : new CPUNumberOfThreadFunction();
        resolver = new MultiThreadRoundResolver(numberOfThreadFunction);
        algorithm = algorithmType.equals("forward")
                ? new StandingOvationForwardCountingAlgorithm()
                : new StandingOvationContestAnalysisAlgorithm();
    }

    @TearDown
    public void tearDown()
            throws Exception {
        resolver.shutdownThreadPool();
        assert largeResult.size() == 100;
        assert smallResult.size() == 100;
    }

    @Benchmark
    public void performanceOfTaskSolvingProcess()
            throws Exception {
        largeResult = resolver.solve(largeRound, algorithm);
    }

    @Benchmark
    public void performanceOfSmallTaskSolvingProcess()
            throws Exception {
        smallResult = resolver.solve(smallRound, algorithm);
    }
}
