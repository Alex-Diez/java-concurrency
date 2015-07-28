package com.google.jam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class StandingOvationResolverTest {

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

	public StandingOvationResolverTest(boolean parallelism) {
		this.parallelism = parallelism;
	}

	private StandingOvationResolver resolver;
	private Map<Integer, Integer> results;

	@Before
	public void setUp()
			throws Exception {
		resolver = new StandingOvationResolver(parallelism);
		results = new LinkedHashMap<>();
		results.put(1, 0);
		results.put(2, 1);
		results.put(3, 2);
		results.put(4, 0);
	}

	@Test
	public void testTaskSolvingProcess()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("test", 'A', "small", "test");
		final RoundCreator creator = new StandingOvationRoundCreator();
		final Round round = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
		final Map<Integer, Integer> resolverResults = resolver.solve(round);
		assertThat(resolverResults, is(equalTo(results)));
	}

	@After
	public void tearDown()
			throws Exception {
		resolver.close();
	}
}
