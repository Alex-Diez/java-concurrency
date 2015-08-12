package com.google.jam.unit.solvers;

import com.google.jam.experiments.CPUNumberOfThreadFunction;
import com.google.jam.experiments.DoubleCPUNumberOfThreadFunction;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

class NumberOfThreadFunctionSupplier
        implements Supplier<Iterator<Supplier<Integer>>> {

    @Override
    public Iterator<Supplier<Integer>> get() {
        return Arrays.asList(
                new DoubleCPUNumberOfThreadFunction(),
                new CPUNumberOfThreadFunction()
        ).iterator();
    }
}
