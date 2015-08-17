package com.google.jam.creators;

import com.google.jam.WrongRoundFormatException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class StandingOvationRoundFunction
        implements Function<List<String>, Collection<String>> {

    @Override
    public Collection<String> apply(final List<String> strings) {
        final int queueLength;
        try {
            final String length = strings.remove(0);
            queueLength = Integer.parseInt(length);
        }
        catch (NumberFormatException e) {
            throw new WrongRoundFormatException();
        }
        if (queueLength != strings.size()) {
            throw new WrongRoundFormatException();
        }
        return new ArrayList<>(strings);
    }
}
