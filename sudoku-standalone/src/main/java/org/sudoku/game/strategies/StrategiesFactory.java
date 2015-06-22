package org.sudoku.game.strategies;

public interface StrategiesFactory {

	Runnable buildBlockResolver(final int rowIndex, final int columnIndex);
}
