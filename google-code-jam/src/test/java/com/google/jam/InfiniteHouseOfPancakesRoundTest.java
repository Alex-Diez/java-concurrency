package com.google.jam;

import org.junit.Ignore;
import org.junit.Test;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

public class InfiniteHouseOfPancakesRoundTest {

	@Test
	@Ignore
	public void testCreateStandingOvationRound()
			throws Exception {
		RoundCreator creator = new InfiniteHouseOfPancakesRoundCreator();
		Round r = new RoundTaskReader(new RoundPathBuilder("test", 'B', "small", "test").build()).applyCreator(creator);
		String task = r.getNextTask();
		assertThat(task, matchesPattern("^([0-9])\\n(([0-9] )*([0-9]))"));
	}
}
