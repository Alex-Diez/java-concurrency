package org.sudoku.game.strategies;

public interface StrategiesFactory {

	Runnable build(int columnIndex, int rowIndex);
}
