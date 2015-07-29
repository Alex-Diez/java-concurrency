package com.google.jam.unit.infinitehouseofpancakes;

import java.util.ArrayList;
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
public class InfiniteHouseOfPancakesRoundTest {

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

	public InfiniteHouseOfPancakesRoundTest(final boolean parallelism) {
		this.parallelism = parallelism;
	}

	@Test
	public void testCreateStandingOvationRound()
			throws Exception {
		final Round r = new Round(parallelism, new ArrayList<>(Arrays.asList("1\n3","4\n1 2 1 2","1\n4")));
		final String task = r.getNextTask();
		assertThat(task, matchesPattern("^([0-9])\\n(([0-9] )*([0-9]))"));
	}
}
