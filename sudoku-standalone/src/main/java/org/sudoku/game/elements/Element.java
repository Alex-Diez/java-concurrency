package org.sudoku.game.elements;

import static org.sudoku.game.elements.Position.STUB;

public class Element
		implements Comparable<Element> {

	public static final Element EMPTY_ELEMENT = new Element(0, STUB);

	public static Element[] getPossibleElements(final int numberOfElementsOnSide) {
		Element[] possibleElements = new Element[numberOfElementsOnSide];
		for (int i = 0; i < numberOfElementsOnSide; i++) {
			possibleElements [i] = new Element.Builder(numberOfElementsOnSide, i + 1, STUB).build();
		}
		return possibleElements;
	}

	final int value;
	public final Position position;

	private Element(final int value, final Position position) {
		this.value = value;
		this.position = position;
	}

	@Override
	public int compareTo(Element e) {
		return e.value - value;
	}

	@Override
	public int hashCode() {
		int result = 31;
		result *= (17 + Integer.hashCode(value));
		result *= (17 + position.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (object != null
				&& object.getClass().equals(getClass())) {
			Element element = (Element) object;
			return element.value == value
					&& element.position.equals(position);
		}
		return false;
	}

	@Override
	public String toString() {
		if (value == 0) {
			return "   ";
		}
		return String.format(" %d ", value);
	}

	public static class Builder {

		private final int value;
		private final Position position;

		public Builder(final int numberOfElementsOnSide, final int value, final Position position) {
			if (value < 1
					|| value > numberOfElementsOnSide) {
				throw new IllegalArgumentException(
						String.format(
								"Value can't be less then 1 and more %d",
								numberOfElementsOnSide
						)
				);
			}
			this.value = value;
			this.position = position;
		}

		public Element build() {
			return new Element(value, position);
		}
	}
}
