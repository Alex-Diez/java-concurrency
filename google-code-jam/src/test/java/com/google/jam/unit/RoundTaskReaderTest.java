package com.google.jam.unit;

import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;
import com.google.jam.creators.RoundCreator;
import com.google.jam.creators.SingleThreadEnvironmentFunction;
import com.google.jam.datastructures.LastIndexTaskLinkedQueue;

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
                new RoundCreator.Builder(new SingleThreadEnvironmentFunction())
                        .setRoundFunction((list) -> new LastIndexTaskLinkedQueue<>())
                        .build()
        );
    }
}
