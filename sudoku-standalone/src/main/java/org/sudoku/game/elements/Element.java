package org.sudoku.game.elements;

import org.sudoku.game.conf.GameFieldConfiguration;

public class Element {

	public static final Element EMPTY_ELEMENT = new Element(0);

	public static Element[] getPossibleElements(final GameFieldConfiguration configuration) {
		final int numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
		Element[] possibleElements = new Element[numberOfElementsOnSide];
		for (int i = 0; i < numberOfElementsOnSide; i++) {
			possibleElements [i] = new Element.Builder(configuration, i + 1).build();
		}
		return possibleElements;
	}

	final int value;

	private Element(final int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return 31 * (17 + Integer.hashCode(value));
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (object != null
				&& object.getClass().equals(getClass())) {
			Element element = (Element) object;
			return element.value == value;
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

		public Builder(final GameFieldConfiguration configuration, final int value) {
			final int numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
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
		}

		public Element build() {
			return new Element(value);
		}
	}
}
