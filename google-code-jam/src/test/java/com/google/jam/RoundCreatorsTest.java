package com.google.jam;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class RoundCreatorsTest {

	@Test
	public void testCreateStandingOvationRoundCreator()
			throws Exception {
		StandingOvationRoundCreator creator = new StandingOvationRoundCreator();
		assertThat(creator, is(instanceOf(RoundCreator.class)));
	}

	@Test
	public void testCreateInfiniteHouseOfPancakesRoundCreator()
			throws Exception {
		InfiniteHouseOfPancakesRoundCreator creator = new InfiniteHouseOfPancakesRoundCreator();
		assertThat(creator, is(instanceOf(RoundCreator.class)));
	}
}
