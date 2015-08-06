package com.google.jam.unit.infinitehouseofpancakes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.WrongRoundFormatException;
import com.google.jam.infinitehouseofpancakes.InfiniteHouseOfPancakesRoundCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class InputInfiniteHouseOfPancakesRoundTest {

    private final String queueLength;
    private RoundCreator creator;

    public InputInfiniteHouseOfPancakesRoundTest(String queueLength) {
        this.queueLength = queueLength;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new String[][] {{"g"}, {""}, {"-4"}, {"1"}, {"100"}});
    }

    @Before
    public void setUp()
            throws Exception {
        creator = new InfiniteHouseOfPancakesRoundCreator();
    }

    @Test(expected = WrongRoundFormatException.class)
    public void testWrongStandingOvationRoundFormat_shouldThrowException()
            throws Exception {
        final Round round = creator.createRound(
                new ArrayList<>(
                        Arrays.asList(
                                queueLength,
                                "1",
                                "3",
                                "4",
                                "1 2 1 2",
                                "1",
                                "4",
                                "5",
                                "4 8 7 8 3"
                        )
                )
        );
    }
}
