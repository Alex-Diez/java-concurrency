package com.google.jam.unit.infinitehouseofpancakes.singlethread;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundResolver;
import com.google.jam.RoundTaskReader;
import com.google.jam.infinitehouseofpancakes.InfiniteHouseOfPancakesRoundCreator;
import com.google.jam.infinitehouseofpancakes.singlethread.SingleThreadInputInfiniteHouseOfPancakesRoundResolverBruteForce;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SingleThreadInputInfiniteHouseOfPancakesRoundResolverTest {

	private RoundResolver resolver;

	@Before
	public void setUp()
			throws Exception {
		resolver = new SingleThreadInputInfiniteHouseOfPancakesRoundResolverBruteForce();
	}

	@Test
	@Ignore
	public void testTaskSolvingProcess()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("test", 'B', "small", "test");
		final RoundCreator creator = new InfiniteHouseOfPancakesRoundCreator();
		final Round round = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
		Map<Integer, Integer> resolverResults = resolver.solve(round, (task) -> null);
		Map<Integer, Integer> results = new LinkedHashMap<>();
		results.put(1, 3);
		results.put(2, 2);
		results.put(3, 3);
		results.put(4, 7);
		assertThat(resolverResults, is(results));
	}
}
