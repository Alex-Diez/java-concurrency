package com.google.jam;

import com.google.jam.creators.RoundCreator;

public interface RoundResolutionFactory {

    RoundCreator buildRoundCreator();
}
