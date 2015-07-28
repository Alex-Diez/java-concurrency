package com.google.jam;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class StandingOvationRoundCreatorTest {

	private RoundCreator creator;

	@Before
	public void setUp()
			throws Exception {
		creator = new StandingOvationRoundCreator();
	}

	@Test
	public void testCreateNotNullStandingOvationRound()
			throws Exception {
		Round round = creator.createRound(new ArrayList<>(Arrays.asList("4", "4 11111", "1 09", "5 110011", "0 1")));
		assertThat(round, is(notNullValue()));
	}

	@Test
	public void testValidateStandingOvationRound()
			throws Exception {
		Round round = creator.createRound(new ArrayList<>(Arrays.asList("4", "4 11111", "1 09", "5 110011", "0 1")));
		String task = round.getNextTask();
		assertThat(task, matchesPattern("^([0-9]*) ([0-9]*)$"));
	}

	@Test(expected = WrongRoundFormatException.class)
	public void testWrongStandingOvationRoundFormat_shouldThrowException()
			throws Exception {
		creator.createRound(new ArrayList<>(Arrays.asList("g", "4 11111", "1 09", "5 110011", "0 1")));
	}
}
