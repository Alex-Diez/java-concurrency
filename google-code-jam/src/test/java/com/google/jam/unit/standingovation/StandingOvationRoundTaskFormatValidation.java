package com.google.jam.unit.standingovation;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.StandingOvationRoundCreator;
import org.junit.Before;
import org.junit.Test;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

public class StandingOvationRoundTaskFormatValidation {

	private RoundCreator creator;

	@Before
	public void setUp()
			throws Exception {
		creator = new StandingOvationRoundCreator();
	}

	@Test
	public void testValidateStandingOvationRound()
			throws Exception {
		Round round = creator.createRound(new ArrayList<>(Arrays.asList("4", "4 11111", "1 09", "5 110011", "0 1")));
		String task = round.getNextTask();
		assertThat(task, matchesPattern("^([0-9]*) ([0-9]*)$"));
	}
}
