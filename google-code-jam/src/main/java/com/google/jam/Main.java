package com.google.jam;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;

public class Main {

	public static void main(String[] args)
			throws Exception {
		RoundPathBuilder smallTaskPathBuilder = new RoundPathBuilder("main", 'A', "small", "practice");
		RoundCreator creator = new StandingOvationRoundCreator();
		Round smallRound = new RoundTaskReader(smallTaskPathBuilder.build()).applyCreator(creator);
		StandingOvationResolver resolver = new StandingOvationResolver(true);
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
