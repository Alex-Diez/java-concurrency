package com.google.jam.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class DijkstraAlgorithm
        implements Function<String, String> {

    private static final Map<Character, Integer> CHARACTER_INTEGER_MAP = new HashMap<Character, Integer>() {
        {
            put('i', 2);
            put('j', 3);
            put('k', 4);
        }
    };

    private static final int[][] MULTIPLICATION_TABLE = {
            {0,  0,  0,  0,  0},
            {0,  1,  2,  3,  4},
            {0,  2, -1,  4, -3},
            {0,  3, -4, -1,  2},
            {0,  4,  3, -2, -1}
    };

    @Override
    public String apply(String task) {

        return null;
    }
}
