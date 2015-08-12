package com.google.jam.unit.creators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.google.jam.Round;
import com.google.jam.creators.RoundCreator;
import com.google.jam.WrongRoundFormatException;
import com.google.jam.creators.StandingOvationRoundCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class InputStandingOvationRoundCreatorTest {

    private final String queueLength;
    private RoundCreator creator;

    public InputStandingOvationRoundCreatorTest(String queueLength) {
        this.queueLength = queueLength;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {"g"}, {""}, {"-4"}, {"1"}, {"100"},
                }
        );
    }

    @Before
    public void setUp()
            throws Exception {
        creator = new StandingOvationRoundCreator();
    }

    @Test(expected = WrongRoundFormatException.class)
    public void testWrongStandingOvationRoundFormat_shouldThrowException()
            throws Exception {
        creator.createRound(new ArrayList<>(Arrays.asList(queueLength, "4 11111", "1 09", "5 110011", "0 1")));
    }

    @Test(expected = WrongRoundFormatException.class)
    public void testWrongStandingOvationRoundFormatMultiThread_shouldThrowException()
            throws Exception {
        creator.createRoundForMultiThreadEnvironment(
                new ArrayList<>(Arrays.asList(queueLength, "4 11111", "1 09", "5 110011", "0 1"))
        );
    }

    @Test
    public void testValidateStandingOvationRound()
            throws Exception {
        final Round round = creator.createRound(
                new ArrayList<>(Arrays.asList("4", "4 11111", "1 09", "5 110011", "0 1"))
        );
        while (round.hasNextTask()) {
            String task = round.getNextTask().getValue();
            assertThat(task, matchesPattern("^([0-9]*) ([0-9]*)$"));
        }
    }

    @Test
    public void testValidateStandingOvationRoundMultiThread()
            throws Exception {
        final Round round = creator.createRoundForMultiThreadEnvironment(
                new ArrayList<>(Arrays.asList("4", "4 11111", "1 09", "5 110011", "0 1"))
        );
        while (round.hasNextTask()) {
            String task = round.getNextTask().getValue();
            assertThat(task, matchesPattern("^([0-9]*) ([0-9]*)$"));
        }
    }
}
