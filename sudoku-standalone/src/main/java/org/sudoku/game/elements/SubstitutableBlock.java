package org.sudoku.game.elements;

import java.util.Set;

public class SubstitutableBlock {

	private final ReadWriteSquare centerSquare;
	private final Set<ReadOnlySquare> vertical;
	private final Set<ReadOnlySquare> horizontal;

	SubstitutableBlock(
			ReadWriteSquare centerSquare,
			Set<ReadOnlySquare> vertical,
			Set<ReadOnlySquare> horizontal) {
		this.centerSquare = centerSquare;
		this.vertical = vertical;
		this.horizontal = horizontal;
	}

	public void readLock() {
	}

	public void readUnlock() {
	}

	public void writeLock() {
	}

	public void writeUnlock() {
	}
}
