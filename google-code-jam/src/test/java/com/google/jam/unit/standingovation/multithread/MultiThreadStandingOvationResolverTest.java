package com.google.jam.unit.standingovation.multithread;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.jam.standingovation.multithread.MultiThreadStandingOvationResolver;
import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;
import com.google.jam.standingovation.StandingOvationResolver;
import com.google.jam.standingovation.StandingOvationRoundCreator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;

public class MultiThreadStandingOvationResolverTest {

	private RoundCreator creator;
	private StandingOvationResolver resolver;

	@Before
	public void setUp()
			throws Exception {
		resolver = new MultiThreadStandingOvationResolver();
		creator = new StandingOvationRoundCreator(true);
	}

	@Test
	public void testTaskSolvingProcess()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("test", 'A', "small", "test");
		final Round round = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
		final Map<Integer, Integer> resolverResults = resolver.solve(round);
		Map<Integer, Integer> results = new LinkedHashMap<>();
		results.put(1, 0);
		results.put(2, 1);
		results.put(3, 2);
		results.put(4, 0);
		assertThat(resolverResults, is(results));
	}

	@Test
	public void testNoNegativeValueInSmallRoundSolution()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("main", 'A', "small", "practice");
		final Round round = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
		final Map<Integer, Integer> resolverResults = resolver.solve(round);
		for (Map.Entry<Integer, Integer> result : resolverResults.entrySet()) {
			assertThat("Negative value on " + result.getKey() + " line", 0, lessThanOrEqualTo(result.getValue()));
		}
	}

	@Test
	public void testNoNegativeValueInLargeRoundSolution()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("main", 'A', "large", "practice");
		final Round round = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
		final Map<Integer, Integer> resolverResults = resolver.solve(round);
		for (Map.Entry<Integer, Integer> result : resolverResults.entrySet()) {
			assertThat("Negative value on " + result.getKey() + " line", 0, lessThanOrEqualTo(result.getValue()));
		}
	}

	@After
	public void tearDown()
			throws Exception {
		resolver.close();
	}
}
