package com.google.jam.algorithms;

import java.util.function.Function;

import static java.lang.Integer.parseInt;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class OminousOminoAlgorithm
        implements Function<String, String> {

    private static final String GABRIEL = "GABRIEL";
    private static final String RICHARD = "RICHARD";

    @Override
    public String apply(final String task) {
        final String[] split = task.split("\\s+");
        final int ominoRate = parseInt(split[0]);
        final int length = parseInt(split[1]);
        final int width = parseInt(split[2]);
        final int large = max(length, width);
        final int small = min(length, width);
        if ((large * small) % ominoRate != 0) {
            return RICHARD;
        }
        if (ominoRate == 3 && small == 1) {
            return RICHARD;
        }
        if (ominoRate == 4 && small <= 2) {
            return RICHARD;
        }
        if (ominoRate == 5 && (small <= 2 || small == 3 && large == 5)) {
            return RICHARD;
        }
        if (ominoRate == 6 && small <= 3) {
            return RICHARD;
        }
        if (ominoRate > 6) {
            return RICHARD;
        }
        return GABRIEL;
    }
}
