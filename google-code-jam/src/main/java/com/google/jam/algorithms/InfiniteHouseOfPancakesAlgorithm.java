package com.google.jam.algorithms;

import java.util.function.Function;

public final class InfiniteHouseOfPancakesAlgorithm
        implements Function<String, String> {

    @Override
    public String apply(String task) {
        final int[] initialPlateWithPancakes = convertRoundTaskToNumbers(task);
        final int maxPancakes = maxElement(initialPlateWithPancakes);
        int minPossibleTime = maxPancakes;
        for (int eatingTime = 1; eatingTime < maxPancakes; eatingTime++) {
            int totalMoves = 0;
            for (int plate : initialPlateWithPancakes) {
                totalMoves += (plate - 1) / eatingTime;
            }
            minPossibleTime = Math.min(minPossibleTime, totalMoves + eatingTime);
        }
        return Integer.toString(minPossibleTime);
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
