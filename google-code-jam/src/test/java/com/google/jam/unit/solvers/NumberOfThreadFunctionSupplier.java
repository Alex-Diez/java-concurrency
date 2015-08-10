package com.google.jam.unit.solvers;

import com.google.jam.experiments.CPUNumberOfThreadFunction;
import com.google.jam.experiments.DoubleCPUNumberOfThreadFunction;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

class NumberOfThreadFunctionSupplier
        implements Supplier<Iterator<Function<Void, Integer>>> {

    @Override
    public Iterator<Function<Void, Integer>> get() {
        return Arrays.asList(
                new DoubleCPUNumberOfThreadFunction(),
                new CPUNumberOfThreadFunction()
        ).iterator();
    }
}
