package com.google.jam;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.google.jam.algorithms.InfiniteHouseOfPancakesContestAnalysisAlgorithm;
import com.google.jam.infinitehouseofpancakes.InfiniteHouseOfPancakesRoundCreator;
import com.google.jam.solvers.RoundResolver;
import com.google.jam.solvers.SingleThreadRoundResolver;

public class Main {

    public static void main(String[] args)
            throws Exception {
        RoundPathBuilder smallTaskPathBuilder = new RoundPathBuilder("main", 'B', "small", "practice");
        RoundCreator creator = new InfiniteHouseOfPancakesRoundCreator();
        Round smallRound = new RoundTaskReader(smallTaskPathBuilder.build()).applyCreator(creator);
        RoundResolver resolver = new SingleThreadRoundResolver();
        Map<Integer, Integer> smallResult = resolver.solve(
                smallRound,
                new InfiniteHouseOfPancakesContestAnalysisAlgorithm());
        ResultWriter smallResultWriter = new ResultWriter(smallResult);
        Path pathToSmall = Paths.get("small");
        if (Files.exists(pathToSmall)) {
            Files.delete(pathToSmall);
        }
        try (BufferedWriter smallWriter = Files.newBufferedWriter(pathToSmall)) {
            smallResultWriter.writeTo(smallWriter);
        }
        RoundPathBuilder largeTaskPathBuilder = new RoundPathBuilder("main", 'B', "large", "practice");
        Round largeRound = new RoundTaskReader(largeTaskPathBuilder.build()).applyCreator(creator);
        Map<Integer, Integer> largeResult = resolver.solve(
                largeRound,
                new InfiniteHouseOfPancakesContestAnalysisAlgorithm());
        ResultWriter largeResultWriter = new ResultWriter(largeResult);
        Path pathToLarge = Paths.get("large");
        if (Files.exists(pathToLarge)) {
            Files.delete(pathToLarge);
        }
        try (BufferedWriter largeWriter = Files.newBufferedWriter(pathToLarge)) {
            largeResultWriter.writeTo(largeWriter);
        }
        System.exit(0);
    }
}
