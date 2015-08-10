package com.google.jam.unit.solvers;

import com.google.jam.algorithms.InfiniteHouseOfPancakesContestAnalysisAlgorithm;
import com.google.jam.algorithms.StandingOvationContestAnalysisAlgorithm;
import com.google.jam.algorithms.StandingOvationForwardCountingAlgorithm;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

public class AlgorithmSupplier
        implements Supplier<Iterator<Function<String, Integer>>> {

    @Override
    public Iterator<Function<String, Integer>> get() {
        return Arrays.asList(
                new InfiniteHouseOfPancakesContestAnalysisAlgorithm(),
                new StandingOvationContestAnalysisAlgorithm(),
                new StandingOvationForwardCountingAlgorithm()
        ).iterator();
    }
}
