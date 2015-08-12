package com.google.jam.unit.creators;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;

public class RegularExpressionSupplier
        implements Supplier<Iterator<String>> {

    @Override
    public Iterator<String> get() {
        return Arrays.asList("^([0-9]*) ([0-9]*)$", "^([0-9]*)( [0-9])*$").iterator();
    }
}
