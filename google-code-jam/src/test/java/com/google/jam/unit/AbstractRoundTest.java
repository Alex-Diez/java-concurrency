package com.google.jam.unit;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public abstract class AbstractRoundTest {

    protected final boolean parallelism;

    public AbstractRoundTest(final boolean parallelism) {
        this.parallelism = parallelism;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Boolean[][] {
                        {false},
                        {true}
                }
        );
    }

    protected Map<Integer, String> createMapFromList(final List<String> data) {
        Map<Integer, String> tasks = new HashMap<>(data.size());
        for (int i = 0; i < data.size(); i++) {
            tasks.put(i + 1, data.get(i));
        }
        return tasks;
    }
}
