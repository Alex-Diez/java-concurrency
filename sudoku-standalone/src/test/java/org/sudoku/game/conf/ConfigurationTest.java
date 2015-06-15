package org.sudoku.game.conf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static java.lang.Math.sqrt;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ConfigurationTest {

	@Parameters
	public static Collection<Integer> conf() {
		return Arrays.asList(9*9, 16*16, 25*25, 36*36, 49*49, 64*64, 81*81);
	}

	private final int numberOfElements;

	public ConfigurationTest(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	private GameFieldConfiguration configuration;

	@Before
	public void setUp() throws Exception {
		configuration = new GameFieldConfiguration.Builder(numberOfElements).build();
	}

	@Test
	public void testNumbersOfSquares() throws Exception {
		assertThat(configuration.getNumberOfSquares(), is(calculateNumberOfSquares()));
	}

	private int calculateNumberOfSquares() {
		return (int) sqrt(numberOfElements);
	}

	@Test
	public void testNumbersOfElements() throws Exception {
		assertThat(configuration.getNumberOfElements(), is(numberOfElements));
	}

	@Test
	public void testNumberOfElementsInColumn() throws Exception {
		assertThat(configuration.getNumberOfElementsInColumn(), is(calculateNumberOfElementsInColumn()));
	}

	private int calculateNumberOfElementsInColumn() {
		return (int) sqrt(numberOfElements);
	}

	@Test
	public void testNumberOfElementsInRow() throws Exception {
		assertThat(configuration.getNumberOfElementsInRow(), is(calculateNumberOfElementsInRow()));
	}

	private int calculateNumberOfElementsInRow() {
		return (int) sqrt(numberOfElements);
	}

	@Test
	public void testNumberOfElementsInSquareColumn() throws Exception {
		assertThat(configuration.getNumberOfElementsInSquareColumn(), is(calculateNumberOfElementsInSquareColumn()));
	}

	private int calculateNumberOfElementsInSquareColumn() {
		return (int) sqrt(sqrt(numberOfElements));
	}

	@Test
	public void testNumberOfElementsInSquareRow() throws Exception {
		assertThat(configuration.getNumberOfSquaresInRow(), is(calculateNumberOfElementsInSquareRow()));
	}

	private int calculateNumberOfElementsInSquareRow() {
		return (int) sqrt(sqrt(numberOfElements));
	}

	@Test
	public void testNumberOfSquaresInColumn() throws Exception {
		assertThat(configuration.getNumberOfSquaresInColumn(), is(calculateNumberOfSquaresInColumn()));
	}

	private int calculateNumberOfSquaresInColumn() {
		return (int) sqrt(sqrt(numberOfElements));
	}

	@Test
	public void testNumberOfSquaresInRow() throws Exception {
		assertThat(configuration.getNumberOfSquaresInRow(), is(calculateNumberOfSquareInRow()));
	}

	private int calculateNumberOfSquareInRow() {
		return (int) sqrt(sqrt(numberOfElements));
	}
}
