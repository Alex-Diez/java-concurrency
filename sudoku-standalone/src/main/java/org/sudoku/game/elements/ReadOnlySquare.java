package org.sudoku.game.elements;

public interface ReadOnlySquare {

	boolean lockForRead();

	Element readFrom(final int rowIndex, final int columnIndex);
}
