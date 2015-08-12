package com.google.jam;

import com.google.jam.creators.RoundCreator;
import com.google.jam.creators.StandingOvationRoundCreator;

public class StandingOvationRoundResolutionFactory
        extends AbstractRoundResolutionFactory
        implements RoundResolutionFactory {

    @Override
    public RoundCreator buildRoundCreator() {
        return new StandingOvationRoundCreator();
    }
}
