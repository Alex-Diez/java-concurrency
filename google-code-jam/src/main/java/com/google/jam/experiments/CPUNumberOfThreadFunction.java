package com.google.jam.experiments;

import java.util.function.Supplier;

public class CPUNumberOfThreadFunction
        implements Supplier<Integer> {

    @Override
    public Integer get() {
        return Runtime.getRuntime().availableProcessors();
    }
}
