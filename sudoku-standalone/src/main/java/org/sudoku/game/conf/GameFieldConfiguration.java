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

	public int getNumberOfElementsOnSide() {
		return (int) sqrt(numberOfElements);
	}

	public int getNumberOfElementsOnSquareSide() {
		return getNumberOfElementsOnSide() / getNumberOfSquaresOnSide();
	}

	public int getNumberOfSquaresOnSide() {
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
