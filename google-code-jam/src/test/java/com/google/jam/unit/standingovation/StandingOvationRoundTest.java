package com.google.jam.unit.standingovation;

import java.util.Arrays;
import java.util.Collection;

import com.google.jam.Round;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class StandingOvationRoundTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(
				new Boolean[][] {
						{false},
						{true}
				}
		);
	}

	private final boolean parallelism;

	public StandingOvationRoundTest(final boolean parallelism) {
		this.parallelism = parallelism;
	}

	@Test
	public void testCreateStandingOvationRound()
			throws Exception {
		final Round round = new Round(parallelism, Arrays.asList("4 11111", "1 09", "5 110011", "0 1"));
		final String task = round.getNextTask();
		assertThat(task, matchesPattern("^([0-9]*) ([0-9]*)$"));
	}
}
