package com.google.jam.creators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import com.google.jam.WrongRoundFormatException;

public class InfiniteHouseOfPancakesRoundFunction
        implements Function<List<String>, Collection<String>> {

    @Override
    public Collection<String> apply(List<String> strings) {
        final int queueLength;
        try {
            final String length = strings.remove(0);
            queueLength = Integer.parseInt(length);
        }
        catch (NumberFormatException e) {
            throw new WrongRoundFormatException();
        }
        if (queueLength != strings.size() / 2) {
            throw new WrongRoundFormatException();
        }
        Collection<String> tasks = new ArrayList<>(strings.size() / 2);
        for (int i = 0; i < strings.size(); i += 2) {
            tasks.add(strings.get(i) + ' ' + strings.get(i + 1));
        }
        return tasks;
    }
}
