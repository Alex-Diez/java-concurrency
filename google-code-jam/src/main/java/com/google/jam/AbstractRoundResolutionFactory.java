package com.google.jam;

import java.util.function.Supplier;

import com.google.jam.solvers.MultiThreadRoundResolver;
import com.google.jam.solvers.RoundResolver;
import com.google.jam.solvers.SingleThreadRoundResolver;

public abstract class AbstractRoundResolutionFactory
        implements RoundResolutionFactory {

    @Override
    public RoundResolver buildSingleThreadRoundResolver() {
        return new SingleThreadRoundResolver();
    }

    @Override
    public RoundResolver buildMultiThreadRoundResolver(final Supplier<Integer> numberOfThreadFunction) {
        return new MultiThreadRoundResolver(numberOfThreadFunction);
    }
}
