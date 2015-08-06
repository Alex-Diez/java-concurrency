package org.sudoku.slave.strategies;

public interface StrategiesFactory {

    Runnable build(int columnIndex, int rowIndex);
}
