package com.google.jam.creators;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class RoundFunctionFactory {

    public Function<List<String>, Collection<String>> createRoundFunction(final char roundLetter) {
        switch (roundLetter) {
            case 'A':
                return new StandingOvationRoundFunction();
            case 'B':
                return new InfiniteHouseOfPancakesRoundFunction();
        }
        throw new RuntimeException("Impossible situation!!!");
    }
}
