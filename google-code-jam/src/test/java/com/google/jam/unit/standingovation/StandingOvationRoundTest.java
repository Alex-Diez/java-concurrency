package com.google.jam.unit.standingovation;

import java.util.Arrays;

import com.google.jam.Round;
import com.google.jam.unit.AbstractRoundTest;

import org.junit.Test;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

public class StandingOvationRoundTest
        extends AbstractRoundTest {

    public StandingOvationRoundTest(final boolean parallelism) {
        super(parallelism);
    }

    @Test
    public void testCreateStandingOvationRound()
            throws Exception {
        final Round round = new Round(
                parallelism, createMapFromList(
                Arrays.asList(
                        "4 11111",
                        "1 09",
                        "5 110011",
                        "0 1")));
        final String task = round.getNextTask().getValue();
        assertThat(task, matchesPattern("^([0-9]*) ([0-9]*)$"));
    }
}
