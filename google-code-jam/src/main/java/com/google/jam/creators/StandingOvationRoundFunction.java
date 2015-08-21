package com.google.jam.creators;

import com.google.jam.WrongRoundFormatException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class StandingOvationRoundFunction
        extends AbstractRoundFunction
        implements Function<List<String>, Collection<String>> {

    @Override
    public Collection<String> apply(final List<String> strings) {
        final int queueLength = parseLength(strings);
        if (queueLength != strings.size()) {
            throw new WrongRoundFormatException();
        }
        return new ArrayList<>(strings);
    }
}
