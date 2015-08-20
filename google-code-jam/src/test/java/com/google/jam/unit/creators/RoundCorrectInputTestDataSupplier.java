package com.google.jam.unit.creators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

class RoundCorrectInputTestDataSupplier
        implements Supplier<Iterator<List<String>>> {

    @Override
    public Iterator<List<String>> get() {
        return Arrays.<List<String>>asList(
                new ArrayList<>(Arrays.asList("4 11111", "1 09", "5 110011", "0 1")),
                new ArrayList<>(Arrays.asList("1", "3", "4", "1 2 1 2", "1", "4", "5", "4 8 7 8 3")),
                new ArrayList<>(Arrays.asList("2 1", "ik", "3 1", "ijk", "3 1", "kji", "2 6", "ji", "1 10000", "i"))
        ).iterator();
    }
}
