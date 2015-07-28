package com.google.jam;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
public class StandingOvationResolverPerformanceTest {

	private Round round;
	private StandingOvationResolver resolver;
	private StandingOvationResolver resolverParallel;

	@Setup
	public void setUp()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("main", 'A', "large", "practice");
		final RoundCreator creator = new StandingOvationRoundCreator();
		round = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
		resolverParallel = new StandingOvationResolver(true);
		resolver = new StandingOvationResolver(false);
	}

	@TearDown
	public void tearDown()
			throws Exception {
		resolver.close();
		resolverParallel.close();
	}

	@Benchmark
	public Map<Integer, Integer> performanceOfSingleThreadStandingOvationResolverTaskSolvingProcess()
			throws Exception {
		return resolver.solve(round);
	}

	@Benchmark
	public Map<Integer, Integer> performanceOfMultiThreadStandingOvationResolverTaskSolvingProcess()
			throws Exception {
		return resolverParallel.solve(round);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(StandingOvationResolverPerformanceTest.class.getSimpleName())
				.build();
		new Runner(opt).run();
	}
}
