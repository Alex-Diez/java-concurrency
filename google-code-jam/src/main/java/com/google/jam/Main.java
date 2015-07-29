package com.google.jam;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;

import com.google.jam.standingovation.AbstractStandingOvationRoundResolver;
import com.google.jam.standingovation.StandingOvationRoundCreator;
import com.google.jam.standingovation.multithread.MultiThreadStandingOvationRoundResolver;

public class Main {

	public static void main(String[] args)
			throws Exception {
		RoundPathBuilder smallTaskPathBuilder = new RoundPathBuilder("main", 'A', "small", "practice");
		RoundCreator creator = new StandingOvationRoundCreator(true);
		Round smallRound = new RoundTaskReader(smallTaskPathBuilder.build()).applyCreator(creator);
		AbstractStandingOvationRoundResolver resolver = new MultiThreadStandingOvationRoundResolver();
		Map<Integer, Integer> smallResult = resolver.solve(smallRound);
		ResultWriter smallResultWriter = new ResultWriter(smallResult);
		BufferedWriter smallWriter = new BufferedWriter(new FileWriter("small"));
		smallResultWriter.writeTo(smallWriter);
		RoundPathBuilder largeTaskPathBuilder = new RoundPathBuilder("main", 'A', "large", "practice");
		Round largeRound = new RoundTaskReader(largeTaskPathBuilder.build()).applyCreator(creator);
		Map<Integer, Integer> largeResult = resolver.solve(largeRound);
		ResultWriter largeResultWriter = new ResultWriter(largeResult);
		BufferedWriter largeWriter = new BufferedWriter(new FileWriter("large"));
		largeResultWriter.writeTo(largeWriter);
		System.exit(0);
	}
}
