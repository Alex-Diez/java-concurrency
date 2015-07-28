package com.google.jam;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;

public class Main {

	public static void main(String[] args)
			throws Exception {
		RoundPathBuilder smallTaskPathBuilder = new RoundPathBuilder("main", 'A', "small", "practice");
//		Round smallRound = new Round(smallTaskPathBuilder.build());
		StandingOvationResolver resolver = new StandingOvationResolver(true);
//		Map<Integer, Integer> smallResult = resolver.solve(smallRound);
//		ResultWriter smallResultWriter = new ResultWriter(smallResult);
		BufferedWriter smallWriter = new BufferedWriter(new FileWriter("small"));
//		smallResultWriter.writeTo(smallWriter);
		RoundPathBuilder largeTaskPathBuilder = new RoundPathBuilder("main", 'A', "large", "practice");
//		Round largeRound = new Round(largeTaskPathBuilder.build());
//		Map<Integer, Integer> largeResult = resolver.solve(largeRound);
//		ResultWriter largeResultWriter = new ResultWriter(largeResult);
		BufferedWriter largeWriter = new BufferedWriter(new FileWriter("large"));
//		largeResultWriter.writeTo(largeWriter);
	}
}
