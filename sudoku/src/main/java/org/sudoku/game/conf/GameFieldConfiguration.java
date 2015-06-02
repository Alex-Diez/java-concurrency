package org.sudoku.game.conf;

public class GameFieldConfiguration {

	private final int numberOfSquares;
	private final int numberOfElements;

	private GameFieldConfiguration(final int numberOfSquares, final int numberOfElements) {
		this.numberOfSquares = numberOfSquares;
		this.numberOfElements = numberOfElements;
	}

	public int getNumberOfSquares() {
		return numberOfSquares;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public int getNumberOfElementsInColumn() {
		return numberOfElements;
	}

	public int getNumberOfElementsInRow() {
		return numberOfElements;
	}

	public int getNumberOfSubstitutableBlocks() {
		return numberOfElements;
	}

	public int getNumberOfElementsInSquareColumn() {
		return getNumberOfElementsInColumn() / getNumberOfSquaresInColumn();
	}

	public int getNumberOfElementsInSquareRow() {
		return getNumberOfElementsInRow() / getNumberOfSquaresInRow();
	}

	public int getNumberOfSquaresInColumn() {
		return (int)Math.sqrt(getNumberOfSquares());
	}

	public int getNumberOfSquaresInRow() {
		return (int)Math.sqrt(getNumberOfSquares());
	}

	public static class Builder {

		private final int numberOfSquares;
		private final int numberOfElements;

		public Builder(final int numberOfSquares, final int numberOfElements) {
			this.numberOfSquares = numberOfSquares;
			this.numberOfElements = numberOfElements;
		}

		public GameFieldConfiguration build() {
			return new GameFieldConfiguration(numberOfSquares, numberOfElements);
		}
	}
}
