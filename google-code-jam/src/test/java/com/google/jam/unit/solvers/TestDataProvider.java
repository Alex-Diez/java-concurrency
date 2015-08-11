package com.google.jam.unit.solvers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TestDataProvider {

    public Map<Integer, Integer> provideSetOfTestData(
            final char roundLetter,
            final String complexity,
            final String type)
            throws IOException {
        final String currentDirectory = System.getProperty("user.dir");
        final String fileSeparator = System.getProperty("file.separator");
        final List<String> data = Files.readAllLines(
                Paths.get(
                        currentDirectory + fileSeparator +
                                "src" + fileSeparator +
                                "test" + fileSeparator +
                                "results" + fileSeparator +
                                roundLetter + '-' + complexity + '-' + type + ".out"
                )
        );
        Map<Integer, Integer> testData = new HashMap<>(data.size());
        data.forEach(
                (datum) -> {
                    String[] split = datum.split("\\s+");
                    int key = Integer.parseInt(split[0]);
                    int value = Integer.parseInt(split[1]);
                    testData.put(key, value);
                }
        );
        return testData;
    }
}
