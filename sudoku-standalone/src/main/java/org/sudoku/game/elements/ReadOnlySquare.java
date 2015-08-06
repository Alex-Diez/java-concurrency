package org.sudoku.game.elements;

import java.util.Collection;

public interface ReadOnlySquare {

    boolean lockForRead();

    Element readFrom(final int rowIndex, final int columnIndex);

    void unlockAfterRead();

    boolean containsElement(final Element element);

    Collection<Position> filledPositions();

    boolean isFilled();

    ReadOnlySquare getLeft();

    ReadOnlySquare setLeft(ReadOnlySquare square);

    ReadOnlySquare getRight();

    ReadOnlySquare setRight(ReadWriteSquare square);

    ReadOnlySquare getUpper();

    ReadOnlySquare setUpper(ReadOnlySquare square);

    ReadOnlySquare getLower();

    ReadOnlySquare setLower(ReadOnlySquare square);

    String printableLine(final int lineNumber);
}
