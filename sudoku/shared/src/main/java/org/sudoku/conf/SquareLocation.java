package org.sudoku.conf;

public enum SquareLocation {

	LEFT("left"),
	UP("up"),
	RIGHT("right"),
	DOWN("down");

	private final String name;

	SquareLocation(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
