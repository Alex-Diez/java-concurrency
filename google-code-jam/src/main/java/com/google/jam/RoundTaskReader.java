package com.google.jam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.jam.creators.RoundCreator;

public class RoundTaskReader {

    private final Path pathToFile;

    public RoundTaskReader(final Path pathToFile) {
        this.pathToFile = pathToFile;
    }

    public Round applyCreator(final RoundCreator roundCreator)
            throws IOException {
        final List<String> fileContent = Files.readAllLines(pathToFile);
        return roundCreator.createRound(fileContent);
    }
}
