package org.sudoku.game.elements;

import org.sudoku.game.conf.GameFieldConfiguration;

public class Element {

	public static final Element EMPTY_ELEMENT = new Element(0);

	public static Element[] getPossibleElements(final GameFieldConfiguration configuration) {
		Element[] possibleElements = new Element[configuration.getNumberOfElementsInColumn()];
		for (int i = 0; i < configuration.getNumberOfElementsInColumn(); i++) {
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
			if (value < 1
					|| value > configuration.getNumberOfElementsInColumn()) {
				throw new IllegalArgumentException(
						String.format(
								"Value can't be less then 1 and more %d",
								configuration.getNumberOfElementsInColumn()
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
