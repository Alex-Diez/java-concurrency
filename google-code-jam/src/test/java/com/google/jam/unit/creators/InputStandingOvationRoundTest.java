package com.google.jam.unit.creators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.google.jam.RoundCreator;
import com.google.jam.WrongRoundFormatException;
import com.google.jam.creators.StandingOvationRoundCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class InputStandingOvationRoundTest {

    private final String queueLength;
    private final boolean parallelism;
    private RoundCreator creator;

    public InputStandingOvationRoundTest(String queueLength, boolean parallelism) {
        this.queueLength = queueLength;
        this.parallelism = parallelism;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {"g", true}, {"", true}, {"-4", true}, {"1", true}, {"100", true},
                        {"g", false}, {"", false}, {"-4", false}, {"1", false}, {"100", false}
                });
    }

    @Before
    public void setUp()
            throws Exception {
        creator = new StandingOvationRoundCreator(parallelism);
    }

    @Test(expected = WrongRoundFormatException.class)
    public void testWrongStandingOvationRoundFormat_shouldThrowException()
            throws Exception {
        creator.createRound(new ArrayList<>(Arrays.asList(queueLength, "4 11111", "1 09", "5 110011", "0 1")));
    }
}
