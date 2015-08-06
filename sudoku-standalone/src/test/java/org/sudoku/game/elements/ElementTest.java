package org.sudoku.game.elements;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sudoku.game.conf.GameFieldConfiguration;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;
import static org.junit.runners.Parameterized.Parameters;
import static org.sudoku.game.elements.Position.STUB;

@RunWith(Parameterized.class)
public class ElementTest {

    private final int numberOfElements;
    private GameFieldConfiguration configuration;
    private List<Element> elements;

    public ElementTest(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Parameters
    public static Collection<Integer> conf() {
        return Arrays.asList(9 * 9, 16 * 16, 25 * 25, 36 * 36, 49 * 49, 64 * 64, 81 * 81);
    }

    @Before
    public void setUp()
            throws Exception {
        configuration = new GameFieldConfiguration.Builder(numberOfElements).build();
        final int numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
        elements = Stream.iterate(
                new Element.Builder(numberOfElementsOnSide, 1, STUB).build(),
                element -> new Element.Builder(numberOfElementsOnSide, element.value + 1, STUB).build()
        ).limit(configuration.getNumberOfElementsOnSide())
                .collect(Collectors.toList());
    }

    @Test
    public void testBuildElementWithAcceptedValue()
            throws Exception {
        final int numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
        new Element.Builder(numberOfElementsOnSide, numberOfElementsOnSide, STUB).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildElementWithUnacceptedValue()
            throws Exception {
        final int numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
        new Element.Builder(numberOfElementsOnSide, numberOfElementsOnSide + 1, STUB).build();
    }

    @Test
    @Ignore
    public void testNumberPossibleElements()
            throws Exception {
        final int numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
        Element[] possibleElements = Element.getPossibleElements(numberOfElementsOnSide);
        assertThat(possibleElements.length, is(numberOfElementsOnSide));
    }

    @Test
    @Ignore
    public void testThatPossibleElementsDoesNotHaveDuplicates()
            throws Exception {
        final int numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
        Element[] possibleElements = Element.getPossibleElements(numberOfElementsOnSide);
        assertThat(Arrays.asList(possibleElements), everyItem(isIn(elements)));
    }

    @Test
    public void elementRepresentationTest() {
        final int numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
        Element e = new Element.Builder(numberOfElementsOnSide, 8, STUB).build();
        assertThat(e.toString(), is(" 8 "));
    }

    @Test
    public void emptyElementRepresentationTest() {
        Element e = Element.EMPTY_ELEMENT;
        assertThat(e.toString(), is("   "));
    }
}
