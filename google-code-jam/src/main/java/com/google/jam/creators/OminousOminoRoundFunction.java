package com.google.jam.creators;

import com.google.jam.WrongRoundFormatException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OminousOminoRoundFunction
        extends AbstractRoundFunction {

    @Override
    public Collection<String> apply(List<String> strings) {
        final int queueLength = parseLength(strings);
        if (queueLength != strings.size()) {
            throw new WrongRoundFormatException();
        }
        return new ArrayList<>(strings);
    }
}
