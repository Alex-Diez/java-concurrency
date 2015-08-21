package com.google.jam.creators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import com.google.jam.WrongRoundFormatException;

public class DijkstraRoundFunction
        extends AbstractRoundFunction
        implements Function<List<String>, Collection<String>> {

    @Override
    public Collection<String> apply(List<String> strings) {
        final int queueLength = parseLength(strings);
        if(queueLength != strings.size() / 2) {
            throw new WrongRoundFormatException();
        }
        final Collection<String> tasks = new ArrayList<>(strings.size() / 2);
        for (int i = 0; i < strings.size(); i += 2) {
            tasks.add(strings.get(i) + ' ' + strings.get(i + 1));
        }
        return tasks;
    }
}
