package org.sudoku.game.elements;

public class Element
		implements Comparable<Element>,
				   Cloneable {

	public static final Element EMPTY_ELEMENT = new Element(0);
	static final Element[] POSSIBLE_ELEMENTS = new Element[] {
			new Element(1),
			new Element(2),
			new Element(3),
			new Element(4),
			new Element(5),
			new Element(6),
			new Element(7),
			new Element(8),
			new Element(9)
	};

	final int value;

	private Element(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return 31 * (17 + Integer.hashCode(value));
	}

	@Override
	public boolean equals(Object object) {
		if(object == this) {
			return true;
		}
		if(object != null
				&& object.getClass().equals(getClass())) {
			Element element = (Element) object;
			return element.value == value;
		}
		return false;
	}

	@Override
	public int compareTo(Element o) {
		return value - o.value;
	}

	@Override
	public Element clone()
			throws CloneNotSupportedException {
		return (Element) super.clone();
	}

	public String toString() {
		return String.format(" %d ", value);
	}

	public static class Builder {

		private final int value;

		public Builder(int value) {
			if(value > 0
					&& value < 10) {
				this.value = value;
			}
			else {
				throw new IllegalArgumentException("Value can't be less then 1 and more 9");
			}
		}

		public Element build() {
			return new Element(value);
		}
	}
}
