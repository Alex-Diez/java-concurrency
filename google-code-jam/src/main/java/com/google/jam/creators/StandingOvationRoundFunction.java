package com.google.jam.creators;

import com.google.jam.WrongRoundFormatException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class StandingOvationRoundFunction
        implements Function<List<String>, Map<Integer, String>> {

    @Override
    public Map<Integer, String> apply(final List<String> strings) {
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
        final Map<Integer, String> tasks = new HashMap<>(strings.size());
        IntStream.range(0, queueLength).forEach((index) -> tasks.put(index + 1, strings.get(index)));
        return tasks;
    }
}
