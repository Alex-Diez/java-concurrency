package org.sudoku.game.elements;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.sudoku.ResolveSudokuGameFieldTest.CONFIGURATION;
import static org.sudoku.ResolveSudokuGameFieldTest.ELEMENTS;

public class SubstitutableBlockTest {

	private SubstitutableBlock substitutableBlock;

	@Before
	public void setUp() {
		final Square centerSquare = new Square.Builder(
				CONFIGURATION,
				ELEMENTS,
				0,
				0,
				new ReentrantReadWriteLock()
		).build();
		final Set<ReadOnlySquare> vertical = buildVerticalSetOfSquares();
		final Set<ReadOnlySquare> horizontal = buildHorizontalSetOfSquares();
		substitutableBlock = new SubstitutableBlock(centerSquare, vertical, horizontal);
	}

	private Set<ReadOnlySquare> buildHorizontalSetOfSquares() {
		final ReadOnlySquare leftSquare = new Square.Builder(
				CONFIGURATION,
				ELEMENTS,
				0,
				2,
				new ReentrantReadWriteLock()
		).build();
		final ReadOnlySquare rightSquare = new Square.Builder(
				CONFIGURATION,
				ELEMENTS,
				0,
				1,
				new ReentrantReadWriteLock()
		).build();
		final Set<ReadOnlySquare> readOnlySquares = new HashSet<>(2, 1.0f);
		readOnlySquares.add(leftSquare);
		readOnlySquares.add(rightSquare);
		return readOnlySquares;
	}

	private Set<ReadOnlySquare> buildVerticalSetOfSquares() {
		final ReadOnlySquare upSquare = new Square.Builder(
				CONFIGURATION,
				ELEMENTS,
				2,
				0,
				new ReentrantReadWriteLock()
		).build();
		final ReadOnlySquare downSquare = new Square.Builder(
				CONFIGURATION,
				ELEMENTS,
				1,
				0,
				new ReentrantReadWriteLock()
		).build();
		final Set<ReadOnlySquare> readOnlySquares = new HashSet<>(2, 1.0f);
		readOnlySquares.add(upSquare);
		readOnlySquares.add(downSquare);
		return readOnlySquares;
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
