package com.google.jam.benchmark.infinitehouseofpancakes;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.RoundPathBuilder;
import com.google.jam.solvers.RoundResolver;
import com.google.jam.RoundTaskReader;
import com.google.jam.creators.InfiniteHouseOfPancakesRoundCreator;
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
        final RoundPathBuilder pathBuilder = new RoundPathBuilder("main", 'B', "large", "practice");
        final RoundCreator creator = new InfiniteHouseOfPancakesRoundCreator();
        round = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
        resolver = new SingleThreadRoundResolver();
    }

    @Benchmark
    public Map<Integer, Integer> performanceOfTaskSolvingProcess()
            throws Exception {
        return resolver.solve(round, (task) -> null);
    }

}
