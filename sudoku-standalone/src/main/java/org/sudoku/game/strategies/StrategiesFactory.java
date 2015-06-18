package org.sudoku.game.strategies;

public interface StrategiesFactory {

	Runnable buildBlockResolverOnColumn(final int columnIndex);

	Runnable buildBlockResolverOnRow(final int rowIndex);
}
