package org.sudoku.game.elements;

import java.util.Collection;

public interface ReadOnlySquare {

	boolean lockForRead();

	Element readFrom(final int rowIndex, final int columnIndex);

	void unlockAfterRead();

	boolean containsElement(final Element element);

	Collection<Integer> filledPositions();

	int getElementPosition(final Element element);
}
