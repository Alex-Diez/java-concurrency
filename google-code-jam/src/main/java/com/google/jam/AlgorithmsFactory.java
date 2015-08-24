package com.google.jam;

import com.google.jam.algorithms.DijkstraAlgorithm;
import com.google.jam.algorithms.InfiniteHouseOfPancakesAlgorithm;
import com.google.jam.algorithms.OminousOminoAlgorithm;
import com.google.jam.algorithms.StandingOvationAlgorithm;

import java.util.function.Function;

public class AlgorithmsFactory {
    public Function<String, String> createAlgorithm(final char letter) {
        switch (letter) {
            case 'A':
                return new StandingOvationAlgorithm();
            case 'B':
                return new InfiniteHouseOfPancakesAlgorithm();
            case 'C':
                return new DijkstraAlgorithm();
            case 'D':
                return new OminousOminoAlgorithm();
        }
        throw new RuntimeException("Impossible situation!!!");
    }
}
