package org.sudoku.net.slave;

import org.sudoku.game.elements.Square;

public class SlaveSudokuResolversManager
		implements Runnable {

	private final SlaveNetworkManager networkManager;
	private Square squareToResolve;

	public SlaveSudokuResolversManager(SlaveNetworkManager networkManager) {
		this.networkManager = networkManager;

	}

	@Override
	public void run() {

	}
}
