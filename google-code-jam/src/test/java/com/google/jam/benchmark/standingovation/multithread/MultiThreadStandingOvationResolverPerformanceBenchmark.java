package com.google.jam.benchmark.standingovation.multithread;

import com.google.jam.MultiThreadRoundResolver;
import com.google.jam.standingovation.multithread.MultiThreadStandingOvationRoundResolver;
import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;
import com.google.jam.standingovation.StandingOvationRoundCreator;
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

import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(1)
public class MultiThreadStandingOvationResolverPerformanceBenchmark {

	private MultiThreadRoundResolver resolver;
	private Round round;

	@Setup
	public void setUp()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("main", 'A', "large", "practice");
		final RoundCreator creator = new StandingOvationRoundCreator(true);
		round = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
		resolver = new MultiThreadStandingOvationRoundResolver();
	}

	@TearDown
	public void tearDown()
			throws Exception {
		resolver.shutdownThreadPool();
	}

	@Benchmark
	public Map<Integer, Integer> performanceOfTaskSolvingProcess()
			throws Exception {
		return resolver.solve(round);
	}
}
