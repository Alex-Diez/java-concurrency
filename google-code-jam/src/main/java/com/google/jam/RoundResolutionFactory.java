package com.google.jam;

import java.util.function.Supplier;

import com.google.jam.creators.RoundCreator;
import com.google.jam.solvers.RoundResolver;

public interface RoundResolutionFactory {

    RoundCreator buildRoundCreator();

    RoundResolver buildSingleThreadRoundResolver();

    RoundResolver buildMultiThreadRoundResolver(final Supplier<Integer> numberOfThreadFunction);
}
