package com.google.jam;

import com.google.jam.creators.RoundCreator;
import com.google.jam.solvers.RoundResolver;
import com.google.jam.solvers.SingleThreadRoundResolver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Main {

    public static void main(String[] args)
            throws IOException {
        final char[] roundLetters = {'A', 'B', 'C'};
        final RoundResolver resolver = new SingleThreadRoundResolver();
        final RoundCreator.Builder builder = new RoundCreator.Builder();
        final RoundFunctionFactory functionFactory = new RoundFunctionFactory();
        final AlgorithmsFactory algorithmsFactory = new AlgorithmsFactory();
        for (char letter : roundLetters) {
            final Function<List<String>, Collection<String>> roundFunction = functionFactory.createRoundFunction(letter);
            final Function<String, String> algorithm = algorithmsFactory.createAlgorithm(letter);
            RoundCreator creator = builder.setRoundFunction(roundFunction).build();
            writeSolvedTaskToDisk(letter, creator, algorithm, resolver, "small");
            writeSolvedTaskToDisk(letter, creator, algorithm, resolver, "large");
        }
        System.exit(0);
    }

    private static void writeSolvedTaskToDisk(
            final char roundLetter,
            final RoundCreator creator,
            final Function<String, String> algorithm,
            final RoundResolver resolver,
            final String taskType)
            throws IOException {
        final RoundPathBuilder taskPathBuilder = new RoundPathBuilder("main", roundLetter, taskType, "practice");
        final Round round = new RoundTaskReader(taskPathBuilder.build()).applyCreator(creator);
        final Map<Integer, String> smallResult = resolver.solve(
                round,
                algorithm
        );
        final ResultWriter resultWriter = new ResultWriter(smallResult);
        final Path pathToResults = Paths.get(taskType + "-" + roundLetter + ".out");
        if (Files.exists(pathToResults)) {
            Files.delete(pathToResults);
        }
        try (BufferedWriter smallWriter = Files.newBufferedWriter(pathToResults)) {
            resultWriter.writeTo(smallWriter);
        }
    }
}
