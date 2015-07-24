package com.google.jam;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;

public class RoundTest {

	private static final Pattern LENGTH_PATTERN = Pattern.compile("^(:digit:)* ");

	private Round small;

	@Before
	public void setUp()
			throws Exception {
		small = new Round('A', "small");
	}

	@Test(expected = IOException.class)
	public void testCreateWrongRound()
			throws Exception {
		new Round('A', "huge");
	}

	@Test
	public void testGetNextTaskLine()
			throws Exception {
		assertThat(anyString(), is(equalTo(small.getNextTask())));
	}

	@Test
	public void testValidateTaskLine()
			throws Exception {
		final String task = small.getNextTask();
		Matcher lengthMatcher = LENGTH_PATTERN.matcher(task);
		assertThat(true, is(lengthMatcher.matches()));
		final String[] taskParts = task.split("\\s+");
		int length;
		try {
			length = Integer.parseInt(String.valueOf(taskParts[0]));
		}
		catch (NumberFormatException e) {
			fail("First numbers in task line has to be number");
			return;
		}
		assertThat(length, is(equalTo(taskParts[1].length())));
	}
}
