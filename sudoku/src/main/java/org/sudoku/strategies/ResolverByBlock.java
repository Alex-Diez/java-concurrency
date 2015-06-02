package org.sudoku.strategies;

import org.sudoku.conf.GameFieldConfiguration;
import org.sudoku.elements.Element;
import org.sudoku.elements.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolverByBlock
		implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ResolverByBlock.class);

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

	private static final Set<Integer> POSSIBLE_POSITIONS = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));

	private final GameFieldConfiguration configuration;
	private final Set<Square> horizontal;
	private final Set<Square> vertical;
	private final Square center;
	private final BlockLock readWriteLock;

	public ResolverByBlock(
			GameFieldConfiguration configuration,
			Square up,
			Lock upReadLock,
			Square down,
			Lock downReadLock,
			Square center,
			ReadWriteLock centerReadWriteLock,
			Square left,
			Lock leftReadLock,
			Square right,
			Lock rightLock) {
		this.configuration = configuration;
		this.horizontal = new HashSet<>(2, 1.0f);
		this.horizontal.add(left);
		this.horizontal.add(right);
		this.vertical = new HashSet<>(2, 1.0f);
		this.vertical.add(up);
		this.vertical.add(down);
		this.center = center;
		this.readWriteLock = new BlockLock(
				upReadLock,
				downReadLock,
				centerReadWriteLock,
				leftReadLock,
				rightLock
		);
	}

	@Override
	public void run() {
		LOG.info("Block before resolution\n{}", center);
		if(!filledEnough()) {
			return;
		}
		readWriteLock.readLock();
		Integer position = -1;
		Element elementToSubstitute = Element.EMPTY_ELEMENT;
		try {
			for (int i = 0; i < Element.POSSIBLE_ELEMENTS.length && position == -1; i++) {
				position = positionToSubstitution(Element.POSSIBLE_ELEMENTS[i]);
				if (position != -1) {
					elementToSubstitute = Element.POSSIBLE_ELEMENTS[i];
				}
			}
		}
		finally {
			readWriteLock.readUnlock();
		}
		if (position != -1) {
			readWriteLock.writeLock();
			try {
				center.putElement(elementToSubstitute, position);
			}
			finally {
				readWriteLock.writeUnlock();
			}
		}
		LOG.info("Block after resolution\n{}", center);
	}

	public boolean filledEnough() {
		return center.size() > 2;
	}

	public Integer positionToSubstitution(Element element) {
		boolean canBeSearchable = !center.hasElement(element);
		if (canBeSearchable) {
			Iterator<Square> iterator = new CompositeIterator<>(
					horizontal.iterator(),
					vertical.iterator()
			);
			Collection<Integer> substitutionPosition = new ArrayList<>(POSSIBLE_POSITIONS);
			while (iterator.hasNext()) {
				Square square = iterator.next();
				Collection<Integer> filledPositions = new ArrayList<>(center.filledPositions());
				filledPositions.addAll(closedPositionsByRows(element));
				filledPositions.addAll(closedPositionsByColumns(element));
				substitutionPosition.removeAll(filledPositions);
			}
			if (substitutionPosition.size() == 1) {
				return substitutionPosition.iterator().next();
			}
		}
		return -1;
	}

	public Collection<Integer> closedPositionsByColumns(Element element) {
		Collection<Integer> positions = new ArrayList<>();
		horizontal.forEach(
				(subSquare) -> {
					if (subSquare.hasElement(element)) {
						Integer position = subSquare.getElementPosition(element);
						Integer rowColPosition = position % configuration.getNumberOfElementsInSquareColumn();
						positions.addAll(COLUMN_CLOSED_POSITIONS.get(rowColPosition));
					}
				}
		);
		return positions;
	}

	public Collection<Integer> closedPositionsByRows(Element element) {
		Collection<Integer> positions = new ArrayList<>();
		vertical.forEach(
				(subSquare) -> {
					if (subSquare.hasElement(element)) {
						Integer position = subSquare.getElementPosition(element);
						Integer rowColPosition = position / configuration.getNumberOfElementsInSquareRow();
						positions.addAll(ROW_CLOSED_POSITIONS.get(rowColPosition));
					}
				}
		);
		return positions;
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

	private static class BlockLock {

		private final Lock upSubSquareLock;
		private final Lock downSubSquareLock;
		private final ReadWriteLock centerSubSquareLock;
		private final Lock leftSubSquareLock;
		private final Lock rightSubSquareLock;

		public BlockLock(
				Lock upSubSquareLock,
				Lock downSubSquareLock,
				ReadWriteLock centerSubSquareLock,
				Lock leftSubSquareLock,
				Lock rightSubSquareLock) {
			this.upSubSquareLock = upSubSquareLock;
			this.downSubSquareLock = downSubSquareLock;
			this.centerSubSquareLock = centerSubSquareLock;
			this.leftSubSquareLock = leftSubSquareLock;
			this.rightSubSquareLock = rightSubSquareLock;
		}

		public void readUnlock() {
			unlock(
					upSubSquareLock,
					downSubSquareLock,
					centerSubSquareLock.readLock(),
					leftSubSquareLock,
					rightSubSquareLock
			);
		}

		public boolean readLock() {
			final boolean locked = lock(
					upSubSquareLock,
					downSubSquareLock,
					centerSubSquareLock.readLock(),
					leftSubSquareLock,
					rightSubSquareLock
			);
			if (!locked) {
				readUnlock();
			}
			return locked;
		}

		public void writeUnlock() {
			unlock(
					upSubSquareLock,
					downSubSquareLock,
					centerSubSquareLock.writeLock(),
					leftSubSquareLock,
					rightSubSquareLock
			);
		}

		public boolean writeLock() {
			final boolean locked = lock(
					upSubSquareLock,
					downSubSquareLock,
					centerSubSquareLock.writeLock(),
					leftSubSquareLock,
					rightSubSquareLock
			);
			if (!locked) {
				readUnlock();
			}
			return locked;
		}

		private static void unlock(Lock... locks) {
			for (Lock lock : locks) {
				lock.unlock();
			}
		}

		private static boolean lock(Lock... locks) {
			boolean result = true;
			for (Lock lock : locks) {
				result &= lock.tryLock();
			}
			return result;
		}
	}
}
