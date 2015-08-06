package org.sudoku.conf;

import java.util.Collection;

import org.sudoku.GameFieldSizeDataProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ConfigurationTest {

    private final int numberOfElements;
    private GameFieldConfiguration configuration;

    public ConfigurationTest(final int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Parameters
    public static Collection configuration() {
        return new GameFieldSizeDataProvider(3).provideData();
    }

    @Before
    public void setUp() {
        configuration = new GameFieldConfiguration.Builder(numberOfElements).build();
    }

    @Test
    public void testConfigurationBuild() {
        assertThat(configuration, is(notNullValue()));
    }

    @Test
    public void testNumberOfElements() {
        assertThat(configuration.getNumberOfElements(), is(numberOfElements));
    }

    @Test
    public void testNumberOfSquares() {
        assertThat(configuration.getNumberOfSquares(), is((int) Math.sqrt(numberOfElements)));
    }

    @Test
    public void testNumberOfElementsInColumn() {
        assertThat(configuration.getNumberOfElementsInColumn(), is((int) Math.sqrt(numberOfElements)));
    }

    @Test
    public void testNumberOfElementsInRow() {
        assertThat(configuration.getNumberOfElementsInRow(), is((int) Math.sqrt(Math.sqrt(numberOfElements))));
    }

    @Test
    public void testNumberOfSubstitutableBlocks() {
        assertThat(configuration.getNumberOfSubstitutableBlocks(), is((int) Math.sqrt(numberOfElements)));
    }

    @Test
    public void testNumberOfElementsInSquareColumn() {
        assertThat(
                configuration.getNumberOfElementsInSquareColumn(),
                is((int) Math.sqrt(numberOfElements / Math.sqrt(numberOfElements)))
        );
    }

    @Test
    public void testNumberOfElementsInSquareRow() {
        assertThat(
                configuration.getNumberOfElementsInSquareRow(),
                is((int) Math.sqrt(numberOfElements / Math.sqrt(numberOfElements)))
        );
    }
}
