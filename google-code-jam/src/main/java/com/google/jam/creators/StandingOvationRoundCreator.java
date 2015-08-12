package com.google.jam.creators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.google.jam.WrongRoundFormatException;

public class StandingOvationRoundCreator
        extends RoundCreator {

    @Override
    protected Map<Integer, String> getIntegerStringMap(List<String> strings) {
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
