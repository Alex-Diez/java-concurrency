package com.google.jam;

import com.google.jam.algorithms.InfiniteHouseOfPancakesContestAnalysisAlgorithm;
import com.google.jam.creators.RoundCreator;
import com.google.jam.creators.RoundFunctionFactory;
import com.google.jam.solvers.RoundResolver;
import com.google.jam.solvers.SingleThreadRoundResolver;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Main {

    public static void main(String[] args)
            throws Exception {
        final char roundLetter = 'B';
        final RoundPathBuilder smallTaskPathBuilder = new RoundPathBuilder("main", roundLetter, "small", "practice");
        final Function<List<String>, Map<Integer, String>> roundFunction = new RoundFunctionFactory().createRoundFunction(
                roundLetter
        );
        final RoundCreator creator = new RoundCreator();
        final Round smallRound = new RoundTaskReader(smallTaskPathBuilder.build()).applyCreator(creator, roundFunction);
        final RoundResolver resolver = new SingleThreadRoundResolver();
        final Map<Integer, Integer> smallResult = resolver.solve(
                smallRound,
                new InfiniteHouseOfPancakesContestAnalysisAlgorithm()
        );
        final ResultWriter smallResultWriter = new ResultWriter(smallResult);
        final Path pathToSmall = Paths.get("small");
        if (Files.exists(pathToSmall)) {
            Files.delete(pathToSmall);
        }
        try (BufferedWriter smallWriter = Files.newBufferedWriter(pathToSmall)) {
            smallResultWriter.writeTo(smallWriter);
        }
        final RoundPathBuilder largeTaskPathBuilder = new RoundPathBuilder("main", roundLetter, "large", "practice");
        final Round largeRound = new RoundTaskReader(largeTaskPathBuilder.build()).applyCreator(creator, roundFunction);
        final Map<Integer, Integer> largeResult = resolver.solve(
                largeRound,
                new InfiniteHouseOfPancakesContestAnalysisAlgorithm()
        );
        final ResultWriter largeResultWriter = new ResultWriter(largeResult);
        final Path pathToLarge = Paths.get("large");
        if (Files.exists(pathToLarge)) {
            Files.delete(pathToLarge);
        }
        try (BufferedWriter largeWriter = Files.newBufferedWriter(pathToLarge)) {
            largeResultWriter.writeTo(largeWriter);
        }
        System.exit(0);
    }
}
