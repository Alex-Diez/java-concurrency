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
	public void testNumberOfElementsOnSide() throws Exception {
		assertThat(configuration.getNumberOfElementsOnSide(), is(calculateNumberOfElementsOnSide()));
	}

	private int calculateNumberOfElementsOnSide() {
		return (int) sqrt(numberOfElements);
	}

	@Test
	public void testNumberOfElementsOnSquareSide() throws Exception {
		assertThat(configuration.getNumberOfElementsOnSquareSide(), is(calculateNumberOfElementsOnSquareSide()));
	}

	private int calculateNumberOfElementsOnSquareSide() {
		return (int) sqrt(sqrt(numberOfElements));
	}

	@Test
	public void testNumberOfSquaresOnSide() throws Exception {
		assertThat(configuration.getNumberOfSquaresOnSide(), is(calculateNumberOfSquaresOnSide()));
	}

	private int calculateNumberOfSquaresOnSide() {
		return (int) sqrt(sqrt(numberOfElements));
	}
}
