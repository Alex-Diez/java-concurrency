package com.google.jam.benchmark;

import com.google.jam.Round;
import com.google.jam.RoundFunctionFactory;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;
import com.google.jam.algorithms.StandingOvationAlgorithm;
import com.google.jam.creators.RoundCreator;
import com.google.jam.experiments.CPUNumberOfThreadFunction;
import com.google.jam.experiments.DoubleCPUNumberOfThreadFunction;
import com.google.jam.solvers.MultiThreadRoundResolver;
import com.google.jam.solvers.RoundResolver;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@State(Scope.Thread)
public class MultiThread {

    @Param({"DOUBLE", "CURRENT"})
    private String functionType;
    @Param({"A", "B", "C", "D"})
    private char roundLetter;

    public RoundResolver resolver;
    public Round largeRound;
    public Function<String, String> algorithm;
    public Map<Integer, String> results;

    @Setup(Level.Invocation)
    public void setUp()
            throws Exception {
        final RoundPathBuilder pathBuilder = new RoundPathBuilder("main", roundLetter, "large", "practice");
        final Function<List<String>, Collection<String>> roundFunction =
                new RoundFunctionFactory().createRoundFunction(roundLetter);
        final RoundCreator creator = new RoundCreator.Builder()
                .setRoundFunction(roundFunction)
                .build();
        largeRound = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
        Supplier<Integer> numberOfThreadFunction = functionType.equals("DOUBLE")
                ? new DoubleCPUNumberOfThreadFunction()
                : new CPUNumberOfThreadFunction();
        resolver = new MultiThreadRoundResolver(numberOfThreadFunction);
        algorithm = new StandingOvationAlgorithm();
    }

    @TearDown(Level.Invocation)
    public void tearDown()
            throws Exception {
        resolver.shutDownResolver();
        assert results != null && results.size() == largeRound.numberOfTasks()
                : "Results should have size " + largeRound.numberOfTasks() + " but has " + results;
    }
}
