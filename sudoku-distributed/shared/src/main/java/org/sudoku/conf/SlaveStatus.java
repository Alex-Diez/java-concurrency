package org.sudoku.conf;

public enum SlaveStatus {

	READY_TO_SERVE, //after slave starts
	SERVE, //slave resolves square cells
	IDLE, //slave needs data to resolve next step
	DONE; //square is resolved

}
