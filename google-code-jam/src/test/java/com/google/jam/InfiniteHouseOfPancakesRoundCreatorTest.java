package com.google.jam;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class InfiniteHouseOfPancakesRoundCreatorTest {

	@Test
	public void testCreateInfiniteHouseOfPancakesRoundCreator()
			throws Exception {
		InfiniteHouseOfPancakesRoundCreator creator = new InfiniteHouseOfPancakesRoundCreator();
		assertThat(creator, is(instanceOf(RoundCreator.class)));
	}
}
