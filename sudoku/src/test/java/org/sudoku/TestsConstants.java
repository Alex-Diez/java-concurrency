package org.sudoku;

import org.sudoku.conf.GameFieldConfiguration;
import org.sudoku.elements.Element;

public class TestsConstants {

	public static final GameFieldConfiguration CONFIGURATION = new GameFieldConfiguration.Builder(9).build();

	public static final Element[][] ELEMENTS = new Element[][] {
			{
					new Element.Builder(CONFIGURATION, 8).build(),
					new Element.Builder(CONFIGURATION, 4).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 5).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 2).build()
			},
			{
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 5).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 9).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 3).build(),
					new Element.Builder(CONFIGURATION, 6).build()
			},
			{
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 2).build(),
					new Element.Builder(CONFIGURATION, 8).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 6).build(),
					new Element.Builder(CONFIGURATION, 4).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT
			},
			{
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 6).build(),
					new Element.Builder(CONFIGURATION, 8).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 1).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 5).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT
			},
			{
					new Element.Builder(CONFIGURATION, 9).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 5).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 4).build()
			},
			{
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 3).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 6).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 7).build(),
					new Element.Builder(CONFIGURATION, 1).build(),
					Element.EMPTY_ELEMENT,
			},
			{
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 9).build(),
					new Element.Builder(CONFIGURATION, 1).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 7).build(),
					new Element.Builder(CONFIGURATION, 3).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT
			},
			{
					new Element.Builder(CONFIGURATION, 2).build(),
					new Element.Builder(CONFIGURATION, 1).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 4).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 8).build(),
					Element.EMPTY_ELEMENT
			},
			{
					new Element.Builder(CONFIGURATION, 7).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 8).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(CONFIGURATION, 2).build(),
					new Element.Builder(CONFIGURATION, 9).build()
			}
	};
}
