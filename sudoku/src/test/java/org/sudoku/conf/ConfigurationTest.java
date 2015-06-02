package org.sudoku.conf;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ConfigurationTest {

	@Parameters
	public static Collection configuration() {
		return Arrays.asList(
				new Object[][] {
						{81},
						{256},
						{625},
						{1296},
						{2401},
						{4096},
						{6561},
						{10000},
						{14641},
						{20736},
						{28561},
						{38416}
				}
		);
	}

	private final int numberOfElements;

	public ConfigurationTest(
			final int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	private GameFieldConfiguration configuration;

	@Before
	public void setUp() {
		configuration = new GameFieldConfiguration.Builder(numberOfElements).build();
	}

	@Test
	public void testNumberOfElements() {
		assertThat(configuration.getNumberOfElements(), is(numberOfElements));
	}

	@Test
	public void testNumberOfSquares() {
		assertThat(configuration.getNumberOfSquares(), is(Math.sqrt(numberOfElements)));
	}

	@Test
	public void testNumberOfElementsInColumn() {
		assertThat(configuration.getNumberOfElementsInColumn(), is(Math.sqrt(numberOfElements)));
	}

	@Test
	public void testNumberOfElementsInRow() {
		assertThat(configuration.getNumberOfElementsInRow(), is(Math.sqrt(Math.sqrt(numberOfElements))));
	}

	@Test
	public void testNumberOfSubstitutableBlocks() {
		assertThat(configuration.getNumberOfSubstitutableBlocks(), is(Math.sqrt(numberOfElements)));
	}

	@Test
	public void testNumberOfElementsInSquareColumn() {
		assertThat(
				configuration.getNumberOfElementsInSquareColumn(),
				is(Math.sqrt(numberOfElements / Math.sqrt(numberOfElements)))
		);
	}

	@Test
	public void testNumberOfElementsInSquareRow() {
		assertThat(
				configuration.getNumberOfElementsInSquareRow(),
				is(Math.sqrt(numberOfElements / Math.sqrt(numberOfElements)))
		);
	}
}
