package com.google.jam.benchmark.infinitehouseofpancakes;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.google.jam.Round;
import com.google.jam.creators.RoundCreator;
import com.google.jam.RoundPathBuilder;
import com.google.jam.creators.RoundFunctionFactory;
import com.google.jam.solvers.RoundResolver;
import com.google.jam.RoundTaskReader;
import com.google.jam.creators.InfiniteHouseOfPancakesRoundFunction;
import com.google.jam.solvers.SingleThreadRoundResolver;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
public class BruteForcePerformanceTest {

    private Round round;
    private RoundResolver resolver;

    public static void main(String[] args)
            throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BruteForcePerformanceTest.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

    @Setup
    public void setUp()
            throws Exception {
        final char roundLetter = 'B';
        final Function<List<String>, Map<Integer, String>> roundFunction =
                new RoundFunctionFactory().createRoundFunction(roundLetter);
        final RoundPathBuilder pathBuilder = new RoundPathBuilder("main", roundLetter, "large", "practice");
        final RoundCreator creator = new RoundCreator();
        round = new RoundTaskReader(pathBuilder.build()).applyCreator(creator, roundFunction);
        resolver = new SingleThreadRoundResolver();
    }

    @Benchmark
    public Map<Integer, Integer> performanceOfTaskSolvingProcess()
            throws Exception {
        return resolver.solve(round, (task) -> null);
    }

}
