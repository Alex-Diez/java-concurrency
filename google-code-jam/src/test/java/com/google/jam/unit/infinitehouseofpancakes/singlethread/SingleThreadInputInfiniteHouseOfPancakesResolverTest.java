package com.google.jam.unit.infinitehouseofpancakes.singlethread;

import java.util.Map;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundResolver;
import com.google.jam.RoundTaskReader;
import com.google.jam.infinitehouseofpancakes.InfiniteHouseOfPancakesRoundCreator;
import com.google.jam.infinitehouseofpancakes.singlethread.SingleThreadInputInfiniteHouseOfPancakesResolver;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SingleThreadInputInfiniteHouseOfPancakesResolverTest {

	private RoundResolver resolver;

	@Before
	public void setUp()
			throws Exception {
		resolver = new SingleThreadInputInfiniteHouseOfPancakesResolver();
	}

	@Test
	public void testTaskSolvingProcess()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("test", 'B', "small", "test");
		final RoundCreator creator = new InfiniteHouseOfPancakesRoundCreator();
		final Round round = new RoundTaskReader(pathBuilder.build()).applyCreator(creator);
		Map<Integer, Integer> results = resolver.solve(round);
		assertThat(results, is(notNullValue()));
	}
}
