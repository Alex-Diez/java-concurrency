package com.google.jam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.jam.creators.RoundCreator;

public class RoundTaskReader {

    private final Path pathToFile;

    public RoundTaskReader(final Path pathToFile) {
        this.pathToFile = pathToFile;
    }

    public Round applyCreator(
            final RoundCreator roundCreator,
            final Function<List<String>, Map<Integer, String>> roundFunction)
            throws IOException {
        final List<String> fileContent = Files.readAllLines(pathToFile);
        return roundCreator.createRound(fileContent, roundFunction);
    }
}
