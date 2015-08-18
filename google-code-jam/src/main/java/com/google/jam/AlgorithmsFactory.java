package com.google.jam;

import java.util.function.Function;

import com.google.jam.algorithms.InfiniteHouseOfPancakesContestAnalysisAlgorithm;
import com.google.jam.algorithms.StandingOvationContestAnalysisAlgorithm;

public class AlgorithmsFactory {
    public Function<String, Integer> createAlgorithm(final char letter) {
        switch (letter) {
            case 'A':
                return new StandingOvationContestAnalysisAlgorithm();
            case 'B':
                return new InfiniteHouseOfPancakesContestAnalysisAlgorithm();
        }
        throw new RuntimeException("Impossible situation!!!");
    }
}
