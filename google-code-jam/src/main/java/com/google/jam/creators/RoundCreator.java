package com.google.jam.creators;

import com.google.jam.Round;
import com.google.jam.datastructures.LastIndexTaskQueue;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class RoundCreator {

    private final Function<List<String>, Collection<String>> roundFunction;
    private final Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction;

    private RoundCreator(
            Function<List<String>, Collection<String>> roundFunction,
            Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction) {
        this.roundFunction = roundFunction;
        this.threadEnvironmentFunction = threadEnvironmentFunction;
    }

    public Round createRound(final List<String> strings) {
        final Collection<String> tasks = roundFunction.apply(strings);
        return new Round(threadEnvironmentFunction.apply(tasks));
    }

    public static class Builder {

        private final Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction;
        private Function<List<String>, Collection<String>> roundFunction;

        public Builder(final Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction) {
            this.threadEnvironmentFunction = threadEnvironmentFunction;
        }

        public Builder setRoundFunction(final Function<List<String>, Collection<String>> roundFunction) {
            this.roundFunction = roundFunction;
            return this;
        }

        public RoundCreator build() {
            return new RoundCreator(roundFunction, threadEnvironmentFunction);
        }
    }
}
