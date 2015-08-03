package com.google.jam.benchmark.standingovation.multithread;

import com.google.jam.MultiThreadRoundResolver;
import com.google.jam.standingovation.AbstractStandingOvationRoundResolver;
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
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
public class MultiThreadStandingOvationResolverPerformanceBenchmark {

	@Param({"forward", "StandingOvationContestAnalysis"})
	private String algorithmType;

	private MultiThreadRoundResolver resolver;
	private Round round;
	private Function<String, Integer> algorithm;

	@Setup
	public void setUp()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("main", 'A', "large", "practice");
		final RoundCreator creator = new StandingOvationRoundCreator(true);
		round = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
		resolver = new MultiThreadStandingOvationRoundResolver();
		algorithm = algorithmType.equals("forward")
				? new AbstractStandingOvationRoundResolver.ForwardCountingAlgorithm()
				: new AbstractStandingOvationRoundResolver.StandingOvationContestAnalysisAlgorithm();
	}

	@TearDown
	public void tearDown()
			throws Exception {
		resolver.shutdownThreadPool();
	}

	@Benchmark
	public Map<Integer, Integer> performanceOfTaskSolvingProcess()
			throws Exception {
		Map<Integer, Integer> result = resolver.solve(round, algorithm);
		assert result.size() == 100;
		return result;
	}

	@Benchmark
	public Map<Integer, Integer> performanceOfWholeProcess()
			throws Exception {
		RoundPathBuilder smallTaskPathBuilder = new RoundPathBuilder("main", 'A', "small", "practice");
		RoundCreator creator = new StandingOvationRoundCreator(true);
		Round smallRound = new RoundTaskReader(smallTaskPathBuilder.build()).applyCreator(creator);
		MultiThreadRoundResolver resolver = new MultiThreadStandingOvationRoundResolver();
		resolver.solve(smallRound, algorithm);
		RoundPathBuilder largeTaskPathBuilder = new RoundPathBuilder("main", 'A', "large", "practice");
		Round largeRound = new RoundTaskReader(largeTaskPathBuilder.build()).applyCreator(creator);
		Map<Integer, Integer> largeResult = resolver.solve(largeRound, algorithm);
		resolver.shutdownThreadPool();
		return largeResult;
	}
}
