package org.sudoku.game.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SubstitutableBlock {

	private static final Map<Integer, Collection<Integer>> ROW_CLOSED_POSITIONS;
	private static final Map<Integer, Collection<Integer>> COLUMN_CLOSED_POSITIONS;

	static {
		ROW_CLOSED_POSITIONS = new HashMap<>();
		ROW_CLOSED_POSITIONS.put(0, Arrays.asList(0, 3, 6));
		ROW_CLOSED_POSITIONS.put(1, Arrays.asList(1, 4, 7));
		ROW_CLOSED_POSITIONS.put(2, Arrays.asList(2, 5, 8));
		COLUMN_CLOSED_POSITIONS = new HashMap<>();
		COLUMN_CLOSED_POSITIONS.put(0, Arrays.asList(0, 1, 2));
		COLUMN_CLOSED_POSITIONS.put(1, Arrays.asList(3, 4, 5));
		COLUMN_CLOSED_POSITIONS.put(2, Arrays.asList(6, 7, 8));
	}

	private final Set<GameField.SubSquare> horizontal;
	private final Set<GameField.SubSquare> vertical;
	private final GameField.SubSquare center;

	private final ReadWriteLock readWriteLock;

	public SubstitutableBlock(
			GameField.SubSquare up,
			GameField.SubSquare down,
			GameField.SubSquare center,
			GameField.SubSquare left,
			GameField.SubSquare right) {
		this.horizontal = new HashSet<>(2, 1.0f);
		this.horizontal.add(left);
		this.horizontal.add(right);
		this.vertical = new HashSet<>(2, 1.0f);
		this.vertical.add(up);
		this.vertical.add(down);
		this.center = center;
		this.readWriteLock = new BlockReadWriteLock(
				new ReentrantReadWriteLock(),
				new ReentrantReadWriteLock(),
				new ReentrantReadWriteLock(),
				new ReentrantReadWriteLock(),
				new ReentrantReadWriteLock()
		);
	}

	public boolean isFilled() {
		return center.size() == 9;
	}

	public Element get(int i, int j) {
		return center.get(i, j);
	}

	public boolean filledEnough() {
		return center.size() > 2;
	}

	public Collection<Integer> filledPositions() {
		return center.filledPositions();
	}

	public Collection<Integer> closedPositionsByRows(Element element) {
		return searchClosedPositions(element, ROW_CLOSED_POSITIONS, horizontal);
	}

	public Collection<Integer> closedPositionsByColumns(Element element) {
		return searchClosedPositions(element, COLUMN_CLOSED_POSITIONS, vertical);
	}

	public void putIn(Element element, Integer position) {
		center.putElement(element, position);
	}

	private static Collection<Integer> searchClosedPositions(
			Element element,
			Map<Integer, Collection<Integer>> closedPositions,
			Collection<GameField.SubSquare> subSquares) {
		Collection<Integer> positions = new ArrayList<>();
		subSquares.forEach(
				(subSquare) -> {
					if (subSquare.hasElement(element)) {
						Integer position = subSquare.getElementPosition(element);
						positions.addAll(closedPositions.get(position));
					}
				}
		);
		return positions;
	}

	public void lockForReading() {
		readWriteLock.readLock().lock();
	}

	public Element elementToSubstitution() {
		for (Element element : Element.POSSIBLE_ELEMENTS) {
			boolean canBeSearchable = !center.hasElement(element);
			if (canBeSearchable) {
				int counter = 0;
				Iterator<GameField.SubSquare> iterator = new CompositeIterator<>(
						horizontal.iterator(),
						vertical.iterator()
				);
				while (iterator.hasNext()) {
					GameField.SubSquare subSquare = iterator.next();
					counter += (subSquare.hasElement(element) ? 1 : 0);
				}
				if (counter > 2) {
					return element;
				}
			}
		}
		return Element.EMPTY_ELEMENT;
	}

	public boolean tryLockForReading() {
		return readWriteLock.readLock().tryLock();
	}

	public boolean tryLockForReading(long time, TimeUnit unit)
			throws InterruptedException {
		return readWriteLock.readLock().tryLock(time, unit);
	}

	public void unlockForReading() {
		readWriteLock.readLock().unlock();
	}

	public void lockForWriting() {
		readWriteLock.writeLock().lock();
	}

	public boolean tryLockForWriting() {
		return readWriteLock.writeLock().tryLock();
	}

	public boolean tryLockForWriting(long time, TimeUnit unit)
			throws InterruptedException {
		return readWriteLock.writeLock().tryLock(time, unit);
	}

	public void unlockForWriting() {
		readWriteLock.writeLock().unlock();
	}

	private static class BlockLock
			implements Lock {

		private final Lock upSubSquareLock;
		private final Lock downSubSquareLock;
		private final Lock centerSubSquareLock;
		private final Lock leftSubSquareLock;
		private final Lock rightSubSquareLock;

		public BlockLock(
				Lock upSubSquareLock,
				Lock downSubSquareLock,
				Lock centerSubSquareLock,
				Lock leftSubSquareLock,
				Lock rightSubSquareLock) {
			this.upSubSquareLock = upSubSquareLock;
			this.downSubSquareLock = downSubSquareLock;
			this.centerSubSquareLock = centerSubSquareLock;
			this.leftSubSquareLock = leftSubSquareLock;
			this.rightSubSquareLock = rightSubSquareLock;
		}

		@Override
		public void lock() {
			upSubSquareLock.lock();
			downSubSquareLock.lock();
			centerSubSquareLock.lock();
			leftSubSquareLock.lock();
			rightSubSquareLock.lock();
		}

		@Override
		public void lockInterruptibly() throws InterruptedException {
			upSubSquareLock.lockInterruptibly();
			downSubSquareLock.lockInterruptibly();
			centerSubSquareLock.lockInterruptibly();
			leftSubSquareLock.lockInterruptibly();
			rightSubSquareLock.lockInterruptibly();
		}

		@Override
		public boolean tryLock() {
			boolean locked = upSubSquareLock.tryLock()
					&& downSubSquareLock.tryLock()
					&& centerSubSquareLock.tryLock()
					&& leftSubSquareLock.tryLock()
					&& rightSubSquareLock.tryLock();
			if (!locked) {
				unlock();
			}
			return locked;
		}

		@Override
		public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
			boolean locked = upSubSquareLock.tryLock(time, unit)
					&& downSubSquareLock.tryLock(time, unit)
					&& centerSubSquareLock.tryLock(time, unit)
					&& leftSubSquareLock.tryLock(time, unit)
					&& rightSubSquareLock.tryLock(time, unit);
			if (!locked) {
				unlock();
			}
			return locked;
		}

		@Override
		public void unlock() {
			upSubSquareLock.unlock();
			downSubSquareLock.unlock();
			centerSubSquareLock.unlock();
			leftSubSquareLock.unlock();
			rightSubSquareLock.unlock();
		}

		@Override
		public Condition newCondition() {
			throw new UnsupportedOperationException();
		}
	}

	private static class BlockReadWriteLock
			implements ReadWriteLock {

		private final Lock readLock;
		private final Lock writeLock;

		public BlockReadWriteLock(
				ReadWriteLock up,
				ReadWriteLock down,
				ReadWriteLock center,
				ReadWriteLock left,
				ReadWriteLock right) {
			this.readLock = new BlockLock(
					up.readLock(),
					down.readLock(),
					center.readLock(),
					left.readLock(),
					right.readLock()
			);
			this.writeLock = new BlockLock(
					up.readLock(),
					down.readLock(),
					center.writeLock(),
					left.readLock(),
					right.readLock()
			);
		}

		@Override
		public Lock readLock() {
			return readLock;
		}

		@Override
		public Lock writeLock() {
			return writeLock;
		}
	}

	private static class CompositeIterator<E>
			implements Iterator<E> {

		private final Iterator<E>[] iterators;
		private int current;

		CompositeIterator(Iterator<E>... iterators) {
			this.iterators = iterators;
			this.current = 0;
		}

		@Override
		public boolean hasNext() {
			while (!iterators[current].hasNext()) {
				current++;
				if (current == iterators.length) {
					return false;
				}
			}
			return iterators[current].hasNext();
		}

		@Override
		public E next() {
			if (hasNext()) {
				return iterators[current].next();
			}
			throw new NoSuchElementException();
		}
	}
}
