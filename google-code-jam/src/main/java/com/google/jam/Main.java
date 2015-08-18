package com.google.jam;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.jam.creators.RoundCreator;
import com.google.jam.creators.SingleThreadEnvironmentFunction;
import com.google.jam.datastructures.LastIndexTaskQueue;
import com.google.jam.solvers.RoundResolver;
import com.google.jam.solvers.SingleThreadRoundResolver;

public class Main {

    public static void main(String[] args)
            throws IOException {
        final char[] roundLetters = {'A', 'B'};
        final RoundResolver resolver = new SingleThreadRoundResolver();
        final Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvFunction =
                new SingleThreadEnvironmentFunction();
        final RoundCreator.Builder builder = new RoundCreator.Builder(threadEnvFunction);
        final RoundFunctionFactory functionFactory = new RoundFunctionFactory();
        final AlgorithmsFactory algorithmsFactory = new AlgorithmsFactory();
        for (char letter : roundLetters) {
            final Function<List<String>, Collection<String>> roundFunction = functionFactory.createRoundFunction(letter);
            final Function<String, Integer> algorithm = algorithmsFactory.createAlgorithm(letter);
            RoundCreator creator = builder.setRoundFunction(roundFunction).build();
            writeSolvedTaskToDisk(letter, creator, algorithm, resolver, roundFunction, "small", threadEnvFunction);
            writeSolvedTaskToDisk(letter, creator, algorithm, resolver, roundFunction, "large", threadEnvFunction);
        }
        System.exit(0);
    }

    private static void writeSolvedTaskToDisk(
            final char roundLetter,
            final RoundCreator creator,
            final Function<String, Integer> algorithm,
            final RoundResolver resolver,
            final Function<List<String>, Collection<String>> roundFunction,
            final String taskType,
            final Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction)
            throws IOException {
        final RoundPathBuilder smallTaskPathBuilder = new RoundPathBuilder("main", roundLetter, taskType, "practice");
        final Round smallRound = new RoundTaskReader(smallTaskPathBuilder.build()).applyCreator(creator);
        final Map<Integer, Integer> smallResult = resolver.solve(
                smallRound,
                algorithm
        );
        final ResultWriter smallResultWriter = new ResultWriter(smallResult);
        final Path pathToResults = Paths.get(taskType + "-" + roundLetter + ".out");
        if (Files.exists(pathToResults)) {
            Files.delete(pathToResults);
        }
        try (BufferedWriter smallWriter = Files.newBufferedWriter(pathToResults)) {
            smallResultWriter.writeTo(smallWriter);
        }
    }
}
