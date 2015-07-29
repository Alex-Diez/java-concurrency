package com.google.jam.unit.standingovation;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundResolver;
import com.google.jam.RoundTaskReader;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;

public abstract class AbstractStandingOvationRoundResolverTest {

	@Test
	public void testTaskSolvingProcess()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("test", 'A', "small", "test");
		final Round round = new RoundTaskReader(pathBuilder.build()).applyCreator(getCreator());
		final Map<Integer, Integer> resolverResults = getResolver().solve(round);
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
		final Round round = new RoundTaskReader(pathBuilder.build()).applyCreator(getCreator());
		final Map<Integer, Integer> resolverResults = getResolver().solve(round);
		for (Map.Entry<Integer, Integer> result : resolverResults.entrySet()) {
			assertThat("Negative value on " + result.getKey() + " line", 0, lessThanOrEqualTo(result.getValue()));
		}
	}

	@Test
	public void testNoNegativeValueInLargeRoundSolution()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("main", 'A', "large", "practice");
		final Round round = new RoundTaskReader(pathBuilder.build()).applyCreator(getCreator());
		final Map<Integer, Integer> resolverResults = getResolver().solve(round);
		for (Map.Entry<Integer, Integer> result : resolverResults.entrySet()) {
			assertThat("Negative value on " + result.getKey() + " line", 0, lessThanOrEqualTo(result.getValue()));
		}
	}

	protected abstract RoundResolver getResolver();

	protected abstract RoundCreator getCreator();
}
