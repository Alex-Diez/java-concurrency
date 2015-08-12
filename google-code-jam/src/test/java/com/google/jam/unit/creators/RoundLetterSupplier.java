package com.google.jam.unit.creators;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;

public class RoundLetterSupplier
        implements Supplier<Iterator<Character>> {

    @Override
    public Iterator<Character> get() {
        return Arrays.asList('A', 'B').iterator();
    }
}
