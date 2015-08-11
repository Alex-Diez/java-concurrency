package com.google.jam.unit.solvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import com.google.jam.algorithms.InfiniteHouseOfPancakesContestAnalysisAlgorithm;
import com.google.jam.algorithms.StandingOvationContestAnalysisAlgorithm;
import com.google.jam.algorithms.StandingOvationForwardCountingAlgorithm;

class AlgorithmSupplier {

    public Iterator<Function<String, Integer>> get(final char roundLetter) {
        switch (roundLetter) {
            case 'A': {
                return Arrays.asList(
                        new StandingOvationContestAnalysisAlgorithm(),
                        new StandingOvationForwardCountingAlgorithm()
                ).iterator();
            }
            case 'B': {
                List<Function<String, Integer>> result = new ArrayList<>();
                result.add(new InfiniteHouseOfPancakesContestAnalysisAlgorithm());
                return result.iterator();
            }
        }
        throw new RuntimeException("Impossible situation!!!");
    }
}
