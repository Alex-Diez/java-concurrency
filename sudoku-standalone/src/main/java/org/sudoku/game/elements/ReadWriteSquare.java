package org.sudoku.game.elements;

public interface ReadWriteSquare
		extends ReadOnlySquare {

	boolean lockForWrite();

	void writeTo(final int rowIndex, final int columnIndex, final Element element);

	void unlockAfterWrite();
}
