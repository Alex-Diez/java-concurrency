package com.google.jam.unit.solvers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TestDataProvider {

    public Map<Integer, String> provideSetOfTestData(
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
        Map<Integer, String> testData = new HashMap<>(data.size());
        data.forEach(
                (datum) -> {
                    final String[] split = datum.split("\\s+");
                    final int key = Integer.parseInt(split[0]);
                    final String value = split[1];
                    testData.put(key, value);
                }
        );
        return testData;
    }
}
