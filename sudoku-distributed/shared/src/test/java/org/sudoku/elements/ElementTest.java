package org.sudoku.elements;

import java.util.Collection;

import org.sudoku.GameFieldSizeDataProvider;
import org.sudoku.conf.GameFieldConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ElementTest {

	@Parameters
	public static Collection configuration() {
		return new GameFieldSizeDataProvider(3).provideData();
	}

	private final GameFieldConfiguration configuration;

	public ElementTest(final int numberOfElements) {
		this.configuration = new GameFieldConfiguration.Builder(numberOfElements).build();
	}

	@Test
	public void buildElementTest() {
		assertThat(new Element.Builder(configuration, 1).build(), is(notNullValue()));
	}

	@Test(expected = IllegalElementNumberException.class)
	public void buildInvalidElement() {
		new Element.Builder(configuration, configuration.getNumberOfElements()).build();
	}
}
