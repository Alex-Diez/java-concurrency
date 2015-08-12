package com.google.jam.unit.creators;

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Supplier;

class TaskQueueRightLengthSupplier
        implements Supplier<Iterator<String>> {

    @Override
    public Iterator<String> get() {
        return Collections.singleton("4").iterator();
    }
}
