package com.google.jam.unit;

import java.util.List;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
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
                    public Round createRound(List<String> strings) {
                        return null;
                    }

                    @Override
                    public Round createRoundForMultiThreadEnvironment(List<String> strings) {
                        return null;
                    }
                }
        );
    }
}
