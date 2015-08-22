package com.google.jam.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntPredicate;

import static java.lang.Math.abs;

public final class DijkstraAlgorithm
        implements Function<String, String> {

    private static final Map<Character, Integer> CHARACTER_INTEGER_MAP = new HashMap<Character, Integer>() {
        {
            put('1', 1);
            put('i', 2);
            put('j', 3);
            put('k', 4);
        }
    };

    private static final int[][] MULTIPLICATION_TABLE = {
            {0, 0, 0, 0, 0},
            {0, 1, 2, 3, 4},
            {0, 2, -1, 4, -3},
            {0, 3, -4, -1, 2},
            {0, 4, 3, -2, -1}
    };

    @Override
    public String apply(String task) {
        final String[] split = task.split("\\s+");
        final long times = Long.parseLong(split[1]);
        String line = split[2];
        if (hasPossibleSolution(times, line)) {
            return "NO";
        }
        final StringReducer reducerToI = new StringReducer();
        if (!reducerToI.couldBeReducedTo((v) -> CHARACTER_INTEGER_MAP.get('i') == v, line, times, (i) -> i == 1)) {
            return "NO";
        }
        final StringReducer reducerToJ = new StringReducer(reducerToI.stopAtTime, reducerToI.stopAtPosition);
        if (!reducerToJ.couldBeReducedTo((v) -> CHARACTER_INTEGER_MAP.get('j') == v, line, times, (i) -> false)) {
            return "NO";
        }
        return "YES";
    }

    private boolean hasPossibleSolution(long times, String line) {
        return multiplyAll(times, line) != -1;
    }

    private int multiplyAll(final long times, final String line) {
        int result = 1;
        for (int j = 0; j < line.length(); j++) {
            final char c = line.charAt(j);
            final int index = CHARACTER_INTEGER_MAP.get(c);
            result = multiply(result, index);
        }
        return power(result, times);
    }

    private int multiply(final int result, final int index) {
        final int sign = index * result > 0 ? 1 : -1;
        return sign * MULTIPLICATION_TABLE[abs(result)][abs(index)];
    }

    private int power(final int base, final long times) {
        int result = 1;
        for (int i = 0; i < times % 4; i++) {
            result = multiply(result, base);
        }
        return result;
    }

    private class StringReducer {
        private final long startAtTime;
        private final int startAtPosition;
        private long stopAtTime;
        private int stopAtPosition;

        public StringReducer() {
            this(0, -1);
        }

        public StringReducer(final long startAtTime, final int startAtPosition) {
            this.startAtTime = startAtTime;
            this.startAtPosition = startAtPosition;
        }

        private boolean couldBeReducedTo(
                final IntPredicate matcher,
                final String line,
                final long times,
                final IntPredicate aheadOfTimePrediction) {
            int result = 1;
            for (long i = startAtTime; i < times; i++) {
                final int start = i == startAtTime ? startAtPosition + 1 : 0;
                for (int j = start; j < line.length(); j++) {
                    final char c = line.charAt(j);
                    final int index = CHARACTER_INTEGER_MAP.get(c);
                    result = multiply(result, index);
                    if (matcher.test(result)) {
                        stopAtTime = i;
                        stopAtPosition = j;
                        return true;
                    }
                }
                if (aheadOfTimePrediction.test(result)) {
                    return false;
                }
                if (i != 0 && i % 4 == 0) {
                    return false;
                }
            }
            return false;
        }
    }
}
