package com.google.jam.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Math.*;

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
        int result = 1;
        for (long i = 0; i < times; i++) {
            for (int j = 0; j < line.length(); j++) {
                final char c = line.charAt(j);
                final int index = CHARACTER_INTEGER_MAP.get(c);
                final int sign = index * result > 0 ? 1 : -1;
                result = sign * MULTIPLICATION_TABLE[abs(result)][abs(index)];
            }
        }
        if (result != -1) {
            return "NO";
        }
        long time = 0;
        int position = 0;
        result = 1;
        for (long i = 0; i < times; i++) {
            for (int j = 0; j < line.length(); j++) {
                final char c = line.charAt(j);
                final int index = CHARACTER_INTEGER_MAP.get(c);
                final int sign = index * result > 0 ? 1 : -1;
                result = sign * MULTIPLICATION_TABLE[abs(result)][abs(index)];
                if (result == 2) {
                    time = i;
                    position = j;
                    break;
                }
            }
            if (result == 2) {
                break;
            }
        }
        result = 1;
        for (long i = time; i < times; i++) {
            int start = i == time ? position + 1 : 0;
            for (int j = start; j < line.length(); j++) {
                final char c = line.charAt(j);
                final int index = CHARACTER_INTEGER_MAP.get(c);
                final int sign = index * result > 0 ? 1 : -1;
                result = sign * MULTIPLICATION_TABLE[abs(result)][abs(index)];
                if (result == 3) {
                    return "YES";
                }
            }
        }
        return "NO";
    }
}
