package com.google.jam;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import com.google.jam.creators.DijkstraRoundFunction;
import com.google.jam.creators.InfiniteHouseOfPancakesRoundFunction;
import com.google.jam.creators.StandingOvationRoundFunction;

public class RoundFunctionFactory {

    public Function<List<String>, Collection<String>> createRoundFunction(final char roundLetter) {
        switch (roundLetter) {
            case 'A':
                return new StandingOvationRoundFunction();
            case 'B':
                return new InfiniteHouseOfPancakesRoundFunction();
            case 'C':
                return new DijkstraRoundFunction();
        }
        throw new RuntimeException("Impossible situation!!!");
    }
}
