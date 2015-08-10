package com.google.jam.unit.creators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.creators.StandingOvationRoundCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class StandingOvationRoundTaskFormatValidation {

    private final boolean parallelism;
    private RoundCreator creator;

    public StandingOvationRoundTaskFormatValidation(final boolean parallelism) {
        this.parallelism = parallelism;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {{false}, {true}});
    }

    @Before
    public void setUp()
            throws Exception {
        creator = new StandingOvationRoundCreator(parallelism);
    }

    @Test
    public void testValidateStandingOvationRound()
            throws Exception {
        Round round = creator.createRound(new ArrayList<>(Arrays.asList("4", "4 11111", "1 09", "5 110011", "0 1")));
        String task = round.getNextTask().getValue();
        assertThat(task, matchesPattern("^([0-9]*) ([0-9]*)$"));
    }
}
