package org.sudoku.game.strategies;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.sudoku.game.elements.Square;

import org.junit.Test;

import static org.sudoku.ResolveSudokuGameFieldTest.CONFIGURATION;
import static org.sudoku.ResolveSudokuGameFieldTest.ELEMENTS;

public class ResolverByBlockTest {

    @Test
    public void testCreateResolverBlock()
            throws Exception {
        new ResolverByBlock(
                new Square.Builder(CONFIGURATION, ELEMENTS, 0, 0, new ReentrantReadWriteLock()).build(),
                CONFIGURATION.getNumberOfElementsOnSide(),
                CONFIGURATION.getNumberOfElementsOnSquareSide()
        );
    }
}
