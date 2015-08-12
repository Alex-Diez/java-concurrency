package com.google.jam;

import com.google.jam.creators.RoundCreator;
import com.google.jam.creators.StandingOvationRoundCreator;

public class StandingOvationRoundResolutionFactory
        implements RoundResolutionFactory {

    @Override
    public RoundCreator buildRoundCreator() {
        return new StandingOvationRoundCreator();
    }
}
