package com.google.jam.unit.creators;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.creators.InfiniteHouseOfPancakesRoundCreator;

import org.junit.Before;
import org.junit.Test;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

public class InputInfiniteHouseOfPancakesRoundTaskFormatValidation {

    private RoundCreator creator;

    @Before
    public void setUp()
            throws Exception {
        creator = new InfiniteHouseOfPancakesRoundCreator();
    }

    @Test
    public void testWrongStandingOvationRoundFormat_shouldThrowException()
            throws Exception {
        final Round round = creator.createRound(
                new ArrayList<>(
                        Arrays.asList(
                                "4",
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
        String task = round.getNextTask().getValue();
        assertThat(task, matchesPattern("^([0-9]*)( [0-9]*)$"));
    }
}
