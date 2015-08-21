package com.google.jam.unit.solvers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;

import com.google.jam.experiments.CPUNumberOfThreadFunction;
import com.google.jam.experiments.DoubleCPUNumberOfThreadFunction;

class NumberOfThreadFunctionSupplier
        implements Supplier<Iterator<Supplier<Integer>>> {

    @Override
    public Iterator<Supplier<Integer>> get() {
        return Arrays.<Supplier<Integer>>asList(
                new DoubleCPUNumberOfThreadFunction(),
                new CPUNumberOfThreadFunction()
        ).iterator();
    }
}
