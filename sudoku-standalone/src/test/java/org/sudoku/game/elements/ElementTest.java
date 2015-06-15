package org.sudoku.game.elements;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.sudoku.game.conf.GameFieldConfiguration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class ElementTest {

	public static Collection<Object> conf() {
		
		Object[][] data = new
		return Arrays.asList(
				new Object[][] {
						new Object[] {
								9 * 9,
								Stream.iterate(1, (i) -> i + 1).limit(9).collect(Collectors.toList())
						},
						new Object[] {
								16 * 16,
								Stream.iterate(1, (i) -> i + 1).limit(16).collect(Collectors.toList())
						},
						new Object[] {
								25 * 25,
								Stream.iterate(1, (i) -> i + 1).limit(25).collect(Collectors.toList())
						},
						new Object[] {
								36 * 36,
								Stream.iterate(1, (i) -> i + 1).limit(36).collect(Collectors.toList())
						},
						new Object[] {
								49 * 49,
								Stream.iterate(1, (i) -> i + 1).limit(49).collect(Collectors.toList())
						},
						new Object[] {
								64 * 64,
								Stream.iterate(1, (i) -> i + 1).limit(64).collect(Collectors.toList())
						},
						new Object[] {
								81 * 81,
								Stream.iterate(1, (i) -> i + 1).limit(81).collect(Collectors.toList())
						}
				}
		);
	}

	private final int numberOfElements;
	private final List<Integer> valuesToBuildElements;
	private final List<Integer> exceptionValues;

	public ElementTest(
			int numberOfElements,
			List<Integer> valuesToBuildElements,
			List<Integer> exceptionValues) {
		this.numberOfElements = numberOfElements;
		this.valuesToBuildElements = valuesToBuildElements;
		this.exceptionValues = exceptionValues;
	}

	private GameFieldConfiguration configuration;

	@Before
	public void setUp() throws Exception {
		configuration = new GameFieldConfiguration.Builder(numberOfElements).build();
	}

	@Test
	public void testBuildExceptedValue() throws Exception {

	}
}
