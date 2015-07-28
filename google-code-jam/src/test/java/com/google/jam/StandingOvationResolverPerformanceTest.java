package com.google.jam;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StandingOvationResolverPerformanceTest {

	private Round round;

	@Setup
	public void setUp()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("main", 'A', "large", "practice");
//		round = new Round(pathBuilder.build());
	}

	@Benchmark
	@Ignore
	public Map<Integer, Integer> performanceOfSingleThreadStandingOvationResolverTaskSolvingProcess()
			throws Exception {
		final StandingOvationResolver resolver = new StandingOvationResolver(false);
		Map<Integer, Integer> results = resolver.solve(round);
		resolver.close();
		return results;
	}

	@Benchmark
	@Ignore
	public Map<Integer, Integer> performanceOfMultiThreadStandingOvationResolverTaskSolvingProcess()
			throws Exception {
		final StandingOvationResolver resolver = new StandingOvationResolver(true);
		Map<Integer, Integer> results = resolver.solve(round);
		resolver.close();
		return results;
	}

/*	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(StandingOvationResolverPerformanceTest.class.getSimpleName())
				.forks(1)
				.warmupIterations(5)
				.measurementIterations(5)
				.build();
		new Runner(opt).run();
	}*/
}
