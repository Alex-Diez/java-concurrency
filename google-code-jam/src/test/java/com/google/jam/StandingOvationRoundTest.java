package com.google.jam;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class StandingOvationRoundTest {

	private Round round;

	@Before
	public void setUp()
			throws Exception {
		round = new Round(4, Arrays.asList("4 11111", "1 09", "5 110011", "0 1"));
	}

	@Test
	public void testValidateTaskLine()
			throws Exception {
		final String task = round.getNextTask();
		assertThat(task, is(notNullValue()));
	}

	@Test
	public void testCreateStandingOvationRound()
			throws Exception {
		String task = round.getNextTask();
		assertThat(task, matchesPattern("^([0-9]*) ([0-9]*)$"));
	}
}
