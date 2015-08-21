package com.google.jam.unit.solvers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;

import com.google.jam.algorithms.DijkstraAlgorithm;
import com.google.jam.algorithms.InfiniteHouseOfPancakesContestAnalysisAlgorithm;
import com.google.jam.algorithms.StandingOvationContestAnalysisAlgorithm;
import com.google.jam.algorithms.StandingOvationForwardCountingAlgorithm;

class AlgorithmSupplier {

    public Iterator<Function<String, String>> get(final char roundLetter) {
        switch (roundLetter) {
            case 'A': {
                return Arrays.asList(
                        new StandingOvationContestAnalysisAlgorithm(),
                        new StandingOvationForwardCountingAlgorithm()
                ).iterator();
            }
            case 'B': {
                return Arrays.<Function<String, String>>asList(
                        new InfiniteHouseOfPancakesContestAnalysisAlgorithm()
                ).iterator();
            }
            case 'C': {
                return Arrays.<Function<String, String>>asList(
                        new DijkstraAlgorithm()
                ).iterator();
            }
        }
        throw new RuntimeException("Impossible situation!!!");
    }
}
