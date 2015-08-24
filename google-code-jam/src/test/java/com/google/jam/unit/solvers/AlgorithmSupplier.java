package com.google.jam.unit.solvers;

import com.google.jam.algorithms.DijkstraAlgorithm;
import com.google.jam.algorithms.InfiniteHouseOfPancakesAlgorithm;
import com.google.jam.algorithms.OminousOminoAlgorithm;
import com.google.jam.algorithms.StandingOvationAlgorithm;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

class AlgorithmSupplier
        implements Supplier<Iterator<Function<String, String>>> {

    public Iterator<Function<String, String>> get() {
        return Arrays.asList(
                new StandingOvationAlgorithm(),
                new InfiniteHouseOfPancakesAlgorithm(),
                new DijkstraAlgorithm(),
                new OminousOminoAlgorithm()
        ).iterator();
    }
}
