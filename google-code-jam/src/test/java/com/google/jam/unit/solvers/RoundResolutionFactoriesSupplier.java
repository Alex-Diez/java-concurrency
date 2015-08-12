package com.google.jam.unit.solvers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;

import com.google.jam.InfiniteHouseOfPancakesRoundResolutionFactory;
import com.google.jam.RoundResolutionFactory;
import com.google.jam.StandingOvationRoundResolutionFactory;

class RoundResolutionFactoriesSupplier
        implements Supplier<Iterator<RoundResolutionFactory>> {

    @Override
    public Iterator<RoundResolutionFactory> get() {
        return Arrays.<RoundResolutionFactory>asList(
                new StandingOvationRoundResolutionFactory(),
                new InfiniteHouseOfPancakesRoundResolutionFactory()
        ).iterator();
    }
}
