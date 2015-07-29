package com.google.jam.benchmark.standingovation;

import com.google.jam.benchmark.standingovation.multithread.MultiThreadStandingOvationResolverPerformanceBenchmark;
import com.google.jam.benchmark.standingovation.singlethread.SingleThreadStandingOvationResolverPerformanceBenchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class StandingOvationResolverPerformanceTest {

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(SingleThreadStandingOvationResolverPerformanceBenchmark.class.getSimpleName())
				.include(MultiThreadStandingOvationResolverPerformanceBenchmark.class.getSimpleName())
				.build();
		new Runner(opt).run();
	}
}
