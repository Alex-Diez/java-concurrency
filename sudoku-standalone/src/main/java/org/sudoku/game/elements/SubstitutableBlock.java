package org.sudoku.game.elements;

import java.util.Set;
import java.util.Collections;
import java.util.stream.Stream;

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

	public boolean readLock() {
		Stream<ReadOnlySquare> stream = Stream.concat(
				Stream.concat(vertical.stream(), horizontal.stream()),
				Collections.singleton(centerSquare).stream()
		);
		boolean isLocked = centerSquare.lockForRead();
		for (ReadOnlySquare v : vertical) {
			isLocked &= v.lockForRead();
		}
		for (ReadOnlySquare h : horizontal) {
			isLocked &= h.lockForRead();
		}
		if (!isLocked) {
			centerSquare.unlockAfterRead();
			vertical.forEach(ReadOnlySquare::unlockAfterRead);
			horizontal.forEach(ReadOnlySquare::unlockAfterRead);
		}
		return isLocked;
	}

	public void readUnlock() {
	}

	public void writeLock() {
	}

	public void writeUnlock() {
	}
}
