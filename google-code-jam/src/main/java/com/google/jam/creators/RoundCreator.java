package com.google.jam.creators;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import com.google.jam.Round;

public class RoundCreator {

    private final Function<List<String>, Collection<String>> roundFunction;

    private RoundCreator(final Function<List<String>, Collection<String>> roundFunction) {
        this.roundFunction = roundFunction;
    }

    public Round createRound(final List<String> strings) {
        final Collection<String> tasks = roundFunction.apply(strings);
        return new Round(tasks);
    }

    @Override
    public String toString() {
        return "[Round creator with " +
                roundFunction.getClass().getSimpleName().replace("RoundFunction", "") +
                " round function]";
    }

    public static class Builder {

        private Function<List<String>, Collection<String>> roundFunction;

        public Builder setRoundFunction(final Function<List<String>, Collection<String>> roundFunction) {
            this.roundFunction = roundFunction;
            return this;
        }

        public RoundCreator build() {
            return new RoundCreator(roundFunction);
        }
    }
}
