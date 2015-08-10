package com.google.jam.experiments;

import java.util.function.Function;

public class CPUNumberOfThreadFunction
        implements Function<Void, Integer> {

    @Override
    public Integer apply(Void aVoid) {
        return Runtime.getRuntime().availableProcessors();
    }
}
