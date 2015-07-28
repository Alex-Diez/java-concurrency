package com.google.jam.unit.standingovation;

import java.util.Arrays;

import com.google.jam.Round;
import org.junit.Before;
import org.junit.Test;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

public class StandingOvationRoundTest {

	@Test
	public void testCreateStandingOvationRound()
			throws Exception {
		final Round round = new Round(4, Arrays.asList("4 11111", "1 09", "5 110011", "0 1"));
		final String task = round.getNextTask();
		assertThat(task, matchesPattern("^([0-9]*) ([0-9]*)$"));
	}
}
