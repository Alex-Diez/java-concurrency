package com.google.jam;

import com.google.jam.creators.InfiniteHouseOfPancakesRoundCreator;
import com.google.jam.creators.RoundCreator;

public class InfiniteHouseOfPancakesRoundResolutionFactory
        extends AbstractRoundResolutionFactory
        implements RoundResolutionFactory {

    @Override
    public RoundCreator buildRoundCreator() {
        return new InfiniteHouseOfPancakesRoundCreator();
    }
}
