package com.google.jam.unit.solvers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;

class RoundLetterSupplier
        implements Supplier<Iterator<Character>> {

    @Override
    public Iterator<Character> get() {
        return Arrays.asList('A', 'B').iterator();
    }
}
