package org.sudoku.strategies;

public interface StrategiesFactory {

	Runnable build(int columnIndex, int rowIndex);
}
