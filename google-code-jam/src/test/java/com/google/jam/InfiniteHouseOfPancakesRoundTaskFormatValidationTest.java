package com.google.jam;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

public class InfiniteHouseOfPancakesRoundTaskFormatValidationTest {

	@Test
	public void testValidateInfiniteHouseOfPancakesRound()
			throws Exception {
		InfiniteHouseOfPancakesRoundCreator creator = new InfiniteHouseOfPancakesRoundCreator();
		Round round = creator.createRound(new ArrayList<>(Arrays.asList("3", "1", "3", "4", "1 2 1 2", "1", "4")));
		String task = round.getNextTask();
		assertThat(task, matchesPattern("^([0-9])\\n(([0-9] )*([0-9]))"));
	}
}
