package com.google.jam.experiments;

import java.util.function.Function;

public class DoubleCPUNumberOfThreadFunction
        implements Function<Void, Integer> {

    @Override
    public Integer apply(Void v) {
        return Runtime.getRuntime().availableProcessors() * 2;
    }
}
