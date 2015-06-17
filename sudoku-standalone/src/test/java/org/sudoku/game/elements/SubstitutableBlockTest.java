package org.sudoku.game.elements;

import org.junit.Before;
import org.junit.Test;

import static org.sudoku.ResolveSudokuGameFieldTest.CONFIGURATION;
import static org.sudoku.ResolveSudokuGameFieldTest.ELEMENTS;

public class SubstitutableBlockTest {

	private SubstitutableBlock substitutableBlock;

	@Before
	public void setUp() {
		final GameField gameField = new GameField.Builder(CONFIGURATION, ELEMENTS).build();
		substitutableBlock = new SubstitutableBlock(gameField, 0, 0);
	}

	@Test
	public void testCreateSubstitutableBlock()
			throws Exception {
	}

	@Test
	public void testLockBlockForReading()
			throws Exception {
		substitutableBlock.readLock();
	}

	@Test
	public void testUnlockAfterReading()
			throws Exception {
		substitutableBlock.readUnlock();
	}

	@Test
	public void testLockBlockForWriting()
			throws Exception {
		substitutableBlock.writeLock();
	}

	@Test
	public void testUnlockAfterWriting()
			throws Exception {
		substitutableBlock.writeUnlock();
	}
}
