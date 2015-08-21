package com.google.jam.algorithms;

import java.util.function.Function;

public final class InfiniteHouseOfPancakesContestAnalysisAlgorithm
        implements Function<String, String> {

    @Override
    public String apply(String task) {
        final int[] array = convertRoundTaskToNumbers(task);
        final int maxPancakes = maxElement(array);
        int ret = maxPancakes;
        for (int i = 1; i < maxPancakes; i++) {
            int totalMoves = 0;
            for (int element : array) {
                totalMoves += (element - 1) / i;
            }
            ret = Math.min(ret, totalMoves + i);
        }
        return Integer.toString(ret);
    }

    private int maxElement(final int[] array) {
        int max = 0;
        for (int element : array) {
            if (max < element) {
                max = element;
            }
        }
        return max;
    }

    private int[] convertRoundTaskToNumbers(final String task) {
        final String[] data = task.split("\\s+");
        final int startedLength = Integer.parseInt(data[0]);
        int[] array = new int[startedLength];
        for (int i = 1; i < data.length; i++) {
            array[i - 1] = Integer.parseInt(data[i]);
        }
        return array;
    }
}
