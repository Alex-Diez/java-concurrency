package com.google.jam.unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.jam.creators.RoundCreator;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;

import org.junit.Before;
import org.junit.Test;

public class RoundTaskReaderTest {

    private RoundTaskReader roundTaskReader;

    @Before
    public void setUp()
            throws Exception {
        roundTaskReader = new RoundTaskReader(new RoundPathBuilder("test", 'A', "small", "test").build());
    }

    @Test
    public void testApplyRoundCreator()
            throws Exception {
        roundTaskReader.applyCreator(
                new RoundCreator() {
                    @Override
                    protected Map<Integer, String> getIntegerStringMap(List<String> strings) {
                        return new HashMap<>();
                    }
                }
        );
    }
}
