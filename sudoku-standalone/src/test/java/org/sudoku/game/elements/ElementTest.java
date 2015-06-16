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

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ElementTest {

	@Parameters
	public static Collection<Integer> conf() {
		return Arrays.asList(9 * 9, 16 * 16, 25 * 25, 36 * 36, 49 * 49, 64 * 64, 81 * 81);
	}

	private final int numberOfElements;

	public ElementTest(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	private GameFieldConfiguration configuration;
	private List<Element> elements;

	@Before
	public void setUp() throws Exception {
		configuration = new GameFieldConfiguration.Builder(numberOfElements).build();
		elements = Stream.iterate(
				new Element.Builder(configuration, 1).build(),
				element -> new Element.Builder(configuration, element.value + 1).build()
		).limit(configuration.getNumberOfElementsInColumn())
				.collect(Collectors.toList());
	}

	@Test
	public void testBuildElementWithAcceptedValue() throws Exception {
		new Element.Builder(configuration, configuration.getNumberOfElementsInColumn()).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildElementWithUnacceptedValue() throws Exception {
		new Element.Builder(configuration, configuration.getNumberOfElementsInColumn() + 1).build();
	}

	@Test
	public void testNumberPossibleElements() throws Exception {
		Element[] possibleElements = Element.getPossibleElements(configuration);
		assertThat(possibleElements.length, is(configuration.getNumberOfElementsInColumn()));
	}

	@Test
	public void testThatPossibleElementsDoesNotHaveDuplicates() throws Exception {
		Element[] possibleElements = Element.getPossibleElements(configuration);
		assertThat(Arrays.asList(possibleElements), everyItem(isIn(elements)));
	}
}
