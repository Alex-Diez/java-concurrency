package org.sudoku;

import org.sudoku.elements.Element;

public class TestsConstants {

	public static final Element[][] ELEMENTS = new Element[][] {
			{
					new Element.Builder(8).build(),
					new Element.Builder(4).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(5).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(2).build()
			},
			{
					Element.EMPTY_ELEMENT,
					new Element.Builder(5).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(9).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(3).build(),
					new Element.Builder(6).build()
			},
			{
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(2).build(),
					new Element.Builder(8).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(6).build(),
					new Element.Builder(4).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT
			},
			{
					Element.EMPTY_ELEMENT,
					new Element.Builder(6).build(),
					new Element.Builder(8).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(1).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(5).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT
			},
			{
					new Element.Builder(9).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(5).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(4).build()
			},
			{
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(3).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(6).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(7).build(),
					new Element.Builder(1).build(),
					Element.EMPTY_ELEMENT,
			},
			{
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(9).build(),
					new Element.Builder(1).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(7).build(),
					new Element.Builder(3).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT
			},
			{
					new Element.Builder(2).build(),
					new Element.Builder(1).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(4).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(8).build(),
					Element.EMPTY_ELEMENT
			},
			{
					new Element.Builder(7).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(8).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(2).build(),
					new Element.Builder(9).build()
			}
	};
}
