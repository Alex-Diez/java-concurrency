package com.google.jam;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RoundTaskReaderTest {

	private RoundTaskReader roundTaskReader;

	@Before
	public void setUp()
			throws Exception {
		roundTaskReader = new RoundTaskReader(new RoundPathBuilder("test", 'A', "small", "test").build());
	}

	@Test
	public void testApplyRoundCreator()
			throws Exception {
		roundTaskReader.applyCreator((strings) -> null);
	}
}
