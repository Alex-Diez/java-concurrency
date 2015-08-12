package com.google.jam.unit.creators;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;

class TaskQueueWrongLengthSupplier
        implements Supplier<Iterator<String>> {

    @Override
    public Iterator<String> get() {
        return Arrays.asList("g", "", "-4", "1", "100").iterator();
    }
}
