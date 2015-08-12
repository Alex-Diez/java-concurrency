package com.google.jam.unit.creators;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;

import com.google.jam.creators.InfiniteHouseOfPancakesRoundCreator;
import com.google.jam.creators.RoundCreator;
import com.google.jam.creators.StandingOvationRoundCreator;

class RoundCreatorClassesSupplier
        implements Supplier<Iterator<Class<? extends RoundCreator>>> {

    @Override
    public Iterator<Class<? extends RoundCreator>> get() {
        return Arrays.asList(
                StandingOvationRoundCreator.class,
                InfiniteHouseOfPancakesRoundCreator.class
        ).iterator();
    }
}
