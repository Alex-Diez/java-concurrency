package org.sudoku.game.conf;

import static java.lang.Math.sqrt;

public class GameFieldConfiguration {

	private final int numberOfElements;

	private GameFieldConfiguration(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public int getNumberOfSquares() {
		return (int)sqrt(numberOfElements);
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public int getNumberOfElementsInColumn() {
		return (int) sqrt(numberOfElements);
	}

	public int getNumberOfElementsInRow() {
		return (int)sqrt(numberOfElements);
	}

	public int getNumberOfElementsInSquareColumn() {
		return getNumberOfElementsInColumn() / getNumberOfSquaresInColumn();
	}

	public int getNumberOfElementsInSquareRow() {
		return getNumberOfElementsInRow() / getNumberOfSquaresInRow();
	}

	public int getNumberOfSquaresInColumn() {
		return (int) sqrt(getNumberOfSquares());
	}

	public int getNumberOfSquaresInRow() {
		return (int) sqrt(getNumberOfSquares());
	}

	public static class Builder {

		private final int numberOfElements;

		public Builder(int numberOfElements) {
			this.numberOfElements = numberOfElements;
		}

		public GameFieldConfiguration build() {
			return new GameFieldConfiguration(numberOfElements);
		}
	}
}
