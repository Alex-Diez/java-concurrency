package org.sudoku.game.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.sudoku.game.elements.Element;
import org.sudoku.game.elements.Position;
import org.sudoku.game.elements.ReadOnlySquare;
import org.sudoku.game.elements.ReadWriteSquare;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.sudoku.game.elements.Position.STUB;

public class ResolverByBlock
        implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ResolverByBlock.class);

    private static final Map<Integer, Collection<Position>> COLUMN_CLOSED_POSITIONS
            = new HashMap<Integer, Collection<Position>>() {
        {
            put(0, Arrays.asList(new Position(0, 0), new Position(0, 1), new Position(0, 2)));
            put(1, Arrays.asList(new Position(1, 0), new Position(1, 1), new Position(1, 2)));
            put(2, Arrays.asList(new Position(2, 0), new Position(2, 1), new Position(2, 2)));
        }
    };

    private static final Map<Integer, Collection<Position>> ROW_CLOSED_POSITIONS
            = new HashMap<Integer, Collection<Position>>() {
        {
            put(0, Arrays.asList(new Position(0, 0), new Position(1, 0), new Position(2, 0)));
            put(1, Arrays.asList(new Position(0, 1), new Position(1, 1), new Position(2, 1)));
            put(2, Arrays.asList(new Position(0, 2), new Position(1, 2), new Position(2, 2)));
        }
    };

    private static final Set<Position> POSSIBLE_POSITIONS = new HashSet<>(
            Arrays.asList(
                    new Position(0, 0),
                    new Position(0, 1),
                    new Position(0, 2),
                    new Position(1, 0),
                    new Position(1, 1),
                    new Position(1, 2),
                    new Position(2, 0),
                    new Position(2, 1),
                    new Position(2, 2)
            )
    );
    private final ReadWriteSquare square;
    private final int numberOfElementsOnSide;
    private final int numberOfElementsOnSquareSide;

    public ResolverByBlock(
            final ReadWriteSquare square,
            final int numberOfElementsOnSide,
            final int numberOfElementsOnSquareSide) {
        this.square = square;
        this.numberOfElementsOnSide = numberOfElementsOnSide;
        this.numberOfElementsOnSquareSide = numberOfElementsOnSquareSide;
    }

    @Override
    public void run() {
        LOG.info("Block before resolution\n{}", square);
        Position position = STUB;
        Element elementToSubstitute = Element.EMPTY_ELEMENT;
        lockForRead();
        try {
            Element[] possibleElements = Element.getPossibleElements(numberOfElementsOnSide);
            for (int i = 0; i < possibleElements.length && position == STUB; i++) {
                position = positionToSubstitution(possibleElements[i]);
                if (position != STUB) {
                    elementToSubstitute = possibleElements[i];
                }
            }
        }
        finally {
            unlockAfterRead();
        }
        if (position != STUB) {
            lockForWrite();
            try {
                int rowIndex = position.row / numberOfElementsOnSquareSide;
                int columnIndex = position.column % numberOfElementsOnSquareSide;
                square.writeTo(rowIndex, columnIndex, elementToSubstitute);
            }
            finally {
                unlockAfterWrite();
            }
        }
        LOG.info("Block after resolution\n{}", square);
    }

    private boolean lockForWrite() {
        boolean result = square.lockForWrite();
        result &= lockVerticalSquares();
        result &= lockHorizontalSquares();
        if (!result) {
            square.unlockAfterWrite();
            unlockVerticalSquares();
            unlockHorizontalSquares();
        }
        return result;
    }

    private void unlockAfterWrite() {
        square.unlockAfterWrite();
        unlockVerticalSquares();
        unlockHorizontalSquares();
    }

    private void unlockAfterRead() {
        square.unlockAfterRead();
        unlockVerticalSquares();
        unlockHorizontalSquares();
    }

    private boolean lockForRead() {
        boolean result = square.lockForRead();
        result &= lockHorizontalSquares();
        result &= lockVerticalSquares();
        if (!result) {
            square.unlockAfterRead();
            unlockVerticalSquares();
            unlockHorizontalSquares();
        }
        return result;
    }

    private void unlockHorizontalSquares() {
        ReadOnlySquare horizontalCurrentSquare;
        horizontalCurrentSquare = square.getLower();
        while (horizontalCurrentSquare != square) {
            horizontalCurrentSquare.unlockAfterRead();
            horizontalCurrentSquare = horizontalCurrentSquare.getLower();
        }
    }

    private void unlockVerticalSquares() {
        ReadOnlySquare verticalCurrentSquare;
        verticalCurrentSquare = square.getLeft();
        while (verticalCurrentSquare != square) {
            verticalCurrentSquare.unlockAfterRead();
            verticalCurrentSquare = verticalCurrentSquare.getLeft();
        }
    }

    private boolean lockHorizontalSquares() {
        boolean result = true;
        ReadOnlySquare horizontalCurrentSquare = square.getLower();
        while (horizontalCurrentSquare != square) {
            result &= horizontalCurrentSquare.lockForRead();
            horizontalCurrentSquare = horizontalCurrentSquare.getLower();
        }
        return result;
    }

    private boolean lockVerticalSquares() {
        boolean result = true;
        ReadOnlySquare verticalCurrentSquare = square.getLeft();
        while (verticalCurrentSquare != square) {
            result &= verticalCurrentSquare.lockForRead();
            verticalCurrentSquare = verticalCurrentSquare.getLeft();
        }
        return result;
    }

    private Position positionToSubstitution(Element element) {
        boolean canBeSearchable = !square.containsElement(element);
        if (canBeSearchable) {
            Collection<Position> substitutionPosition = new ArrayList<>(POSSIBLE_POSITIONS);
            Collection<Position> filledPositions = new ArrayList<>(square.filledPositions());
            filledPositions.addAll(closedPositionsByRows(element));
            filledPositions.addAll(closedPositionsByColumns(element));
            substitutionPosition.removeAll(filledPositions);
            if (substitutionPosition.size() == 1) {
                return substitutionPosition.iterator().next();
            }
        }
        return STUB;
    }

    private Collection<Position> closedPositionsByColumns(Element element) {
        Collection<Position> positions = new ArrayList<>();
        ReadOnlySquare horizontalCurrentSquare = square.getLeft();
        while (horizontalCurrentSquare != square) {
            if (horizontalCurrentSquare.containsElement(element)) {
                Integer colPosition = element.position.column / numberOfElementsOnSquareSide;
                positions.addAll(COLUMN_CLOSED_POSITIONS.get(colPosition));
            }
            horizontalCurrentSquare = horizontalCurrentSquare.getLeft();
        }
        return positions;
    }

    private Collection<Position> closedPositionsByRows(Element element) {
        Collection<Position> positions = new ArrayList<>();
        ReadOnlySquare verticalCurrentSquare = square.getLower();
        while (verticalCurrentSquare != square) {
            if (verticalCurrentSquare.containsElement(element)) {
                Integer rowPosition = element.position.row % numberOfElementsOnSquareSide;
                positions.addAll(ROW_CLOSED_POSITIONS.get(rowPosition));
            }
            verticalCurrentSquare = verticalCurrentSquare.getLower();
        }
        return positions;
    }
}
