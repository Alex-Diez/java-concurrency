package com.google.jam.unit.solvers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;

class TestDataLocationSupplier
        implements Supplier<Iterator<String[]>> {

    @Override
    public Iterator<String[]> get() {
        return Arrays.asList(
                new String[][] {
                        {"test", "small", "test", "main", "small", "practice", "main", "large", "practice"}
                }
        ).iterator();
    }
}
