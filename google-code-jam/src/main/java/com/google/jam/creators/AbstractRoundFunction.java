package com.google.jam.creators;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import com.google.jam.WrongRoundFormatException;

abstract class AbstractRoundFunction
        implements Function<List<String>, Collection<String>> {

    protected int parseLength(final List<String> strings) {
        try {
            final String length = strings.remove(0);
            return Integer.parseInt(length);
        }
        catch (NumberFormatException e) {
            throw new WrongRoundFormatException();
        }
    }
}
