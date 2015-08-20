package com.google.jam.benchmark.standingovation.singlethread;

import com.google.jam.Round;
import com.google.jam.RoundFunctionFactory;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;
import com.google.jam.algorithms.StandingOvationContestAnalysisAlgorithm;
import com.google.jam.creators.RoundCreator;
import com.google.jam.solvers.RoundResolver;
import com.google.jam.solvers.SingleThreadRoundResolver;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@State(Scope.Thread)
public class SingleThread {

    public Round largeRound;
    public RoundResolver resolver;
    public Function<String, Integer> algorithm;
    public Map<Integer, Integer> results;

    @Setup(Level.Iteration)
    public void setUp()
            throws Exception {
        final char roundLetter = 'A';
        final RoundPathBuilder largeTaskPathBuilder = new RoundPathBuilder("main", roundLetter, "large", "practice");
        final Function<List<String>, Collection<String>> roundFunction =
                new RoundFunctionFactory().createRoundFunction(roundLetter);
        final RoundCreator creator = new RoundCreator.Builder()
                .setRoundFunction(roundFunction)
                .build();
        largeRound = new RoundTaskReader(largeTaskPathBuilder.build()).applyCreator(creator);
        resolver = new SingleThreadRoundResolver();
        algorithm = new StandingOvationContestAnalysisAlgorithm();
    }

    @TearDown(Level.Iteration)
    public void tearDown()
            throws Exception {
        resolver.shutDownResolver();
        assert results != null && results.size() == largeRound.numberOfTasks()
                : "Results should have size " + largeRound.numberOfTasks() + " but has " + results;
    }
}
