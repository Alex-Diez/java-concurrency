package com.google.jam.unit.infinitehouseofpancakes;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.jam.Round;
import org.junit.Test;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

public class InfiniteHouseOfPancakesRoundTest {

	@Test
	public void testCreateStandingOvationRound()
			throws Exception {
		final Round r = new Round(3, new ArrayList<>(Arrays.asList("1\n3","4\n1 2 1 2","1\n4")));
		final String task = r.getNextTask();
		assertThat(task, matchesPattern("^([0-9])\\n(([0-9] )*([0-9]))"));
	}
}
