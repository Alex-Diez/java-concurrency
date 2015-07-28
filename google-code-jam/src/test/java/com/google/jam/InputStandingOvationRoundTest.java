package com.google.jam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class InputStandingOvationRoundTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new String[][] {{"g"}, {""}, {"-4"}, {"1"}, {"100"}});
	}

	private final String queueLength;

	public InputStandingOvationRoundTest(String queueLength) {
		this.queueLength = queueLength;
	}

	private RoundCreator creator;

	@Before
	public void setUp()
			throws Exception {
		creator = new StandingOvationRoundCreator();
	}

	@Test(expected = WrongRoundFormatException.class)
	public void testWrongStandingOvationRoundFormat_shouldThrowException()
			throws Exception {
		creator.createRound(new ArrayList<>(Arrays.asList(queueLength, "4 11111", "1 09", "5 110011", "0 1")));
	}
}
