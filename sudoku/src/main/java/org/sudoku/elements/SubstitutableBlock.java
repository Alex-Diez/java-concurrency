package org.sudoku.elements;

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

import org.sudoku.conf.GameFieldConfiguration;

public class SubstitutableBlock {

	private static final Map<Integer, Collection<Integer>> COLUMN_CLOSED_POSITIONS;
	private static final Map<Integer, Collection<Integer>> ROW_CLOSED_POSITIONS;

	static {
		COLUMN_CLOSED_POSITIONS = new HashMap<>();
		COLUMN_CLOSED_POSITIONS.put(0, Arrays.asList(0, 3, 6));
		COLUMN_CLOSED_POSITIONS.put(1, Arrays.asList(1, 4, 7));
		COLUMN_CLOSED_POSITIONS.put(2, Arrays.asList(2, 5, 8));
		ROW_CLOSED_POSITIONS = new HashMap<>();
		ROW_CLOSED_POSITIONS.put(0, Arrays.asList(0, 1, 2));
		ROW_CLOSED_POSITIONS.put(1, Arrays.asList(3, 4, 5));
		ROW_CLOSED_POSITIONS.put(2, Arrays.asList(6, 7, 8));
	}

	private final Set<Square> horizontal;
	private final Set<Square> vertical;
	private final Square center;
	private final GameFieldConfiguration configuration;

	private final ReadWriteLock readWriteLock;

	private SubstitutableBlock(
			final GameFieldConfiguration configuration,
			Square up,
			Square down,
			Square center,
			Square left,
			Square right) {
		this.configuration = configuration;
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
		Collection<Integer> positions = new ArrayList<>();
		horizontal.forEach(
				(subSquare) -> {
					if(subSquare.hasElement(element)) {
						Integer position = subSquare.getElementPosition(element);
						Integer rowColPosition = position / configuration.getNumberOfElementsInSquareRow();
						positions.addAll(ROW_CLOSED_POSITIONS.get(rowColPosition));
					}
				}
		);
		return positions;
	}

	public Collection<Integer> closedPositionsByColumns(Element element) {
		Collection<Integer> positions = new ArrayList<>();
		vertical.forEach(
				(subSquare) -> {
					if(subSquare.hasElement(element)) {
						Integer position = subSquare.getElementPosition(element);
						Integer rowColPosition = position % configuration.getNumberOfElementsInSquareColumn();
						positions.addAll(COLUMN_CLOSED_POSITIONS.get(rowColPosition));
					}
				}
		);
		return positions;
	}

	public void putIn(Element element, Integer position) {
		center.putElement(element, position);
	}

	public void lockForReading() {
		readWriteLock.readLock().lock();
	}

	public Element elementToSubstitution() {
		for(Element element : Element.POSSIBLE_ELEMENTS) {
			boolean canBeSearchable = !center.hasElement(element);
			if(canBeSearchable) {
				int counter = 0;
				Iterator<Square> iterator = new CompositeIterator<>(
						horizontal.iterator(),
						vertical.iterator()
				);
				while(iterator.hasNext()) {
					Square square = iterator.next();
					counter += (square.hasElement(element) ? 1 : 0);
				}
				if(counter > 2) {
					return element;
				}
			}
		}
		return Element.EMPTY_ELEMENT;
	}

	@Override
	public String toString() {
		return center.toString();
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
		public void lockInterruptibly()
				throws InterruptedException {
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
			if(!locked) {
				unlock();
			}
			return locked;
		}

		@Override
		public boolean tryLock(long time, TimeUnit unit)
				throws InterruptedException {
			boolean locked = upSubSquareLock.tryLock(time, unit)
					&& downSubSquareLock.tryLock(time, unit)
					&& centerSubSquareLock.tryLock(time, unit)
					&& leftSubSquareLock.tryLock(time, unit)
					&& rightSubSquareLock.tryLock(time, unit);
			if(!locked) {
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
			while(!iterators[current].hasNext()) {
				current++;
				if(current == iterators.length) {
					return false;
				}
			}
			return iterators[current].hasNext();
		}

		@Override
		public E next() {
			if(hasNext()) {
				return iterators[current].next();
			}
			throw new NoSuchElementException();
		}
	}

	public static class Builder {
		public Builder(
				final GameFieldConfiguration configuration,
				final GameField gameField,
				final int columnIndex,
				final int rowIndex) {

		}

		public SubstitutableBlock build() {
			return null;
		}
	}
}
