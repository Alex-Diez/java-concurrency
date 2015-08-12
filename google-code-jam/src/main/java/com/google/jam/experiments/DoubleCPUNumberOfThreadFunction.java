package com.google.jam.experiments;

import java.util.function.Supplier;

public class DoubleCPUNumberOfThreadFunction
        implements Supplier<Integer> {

    @Override
    public Integer get() {
        return Runtime.getRuntime().availableProcessors() * 2;
    }
}
