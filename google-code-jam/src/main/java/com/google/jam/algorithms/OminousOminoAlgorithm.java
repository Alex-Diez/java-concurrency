package com.google.jam.algorithms;

import java.util.function.Function;

public class OminousOminoAlgorithm
        implements Function<String, String> {

    private static final String GABRIEL = "GABRIEL";
    public static final String RICHARD = "RICHARD";

    @Override
    public String apply(final String task) {
        final String[] split = task.split("\\s+");
        final int ominoRate = Integer.parseInt(split[0]);
        final int length = Integer.parseInt(split[1]);
        final int width = Integer.parseInt(split[2]);
        if (length % ominoRate == 0) {
            if (width % ominoRate == 1) {
                return RICHARD;
            }
            if(width % ominoRate == 0) {
                return GABRIEL;
            }
        }
        if (width % ominoRate == 0) {
            if (length % ominoRate == 1) {
                return RICHARD;
            }
            if(length % ominoRate == 0) {
                return GABRIEL;
            }
        }
        if((length * width) % ominoRate > 0) {
            return RICHARD;
        }
        return GABRIEL;
    }
}
