package org.sudoku;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameFieldSizeDataProvider {

	private static final int NUMBER_OF_TESTS = 10;

	private int counterInitialValue = 3;

	public GameFieldSizeDataProvider(final int counterInitialValue) {
		this.counterInitialValue = counterInitialValue;
	}

	public Collection<Integer> provideData() {
		return Stream.generate(
				() -> {
					final int testCounter = retrieveTestCounter();
					return testCounter * testCounter;
				}
		).limit(NUMBER_OF_TESTS).collect(Collectors.toList());
	}

	private int retrieveTestCounter() {
		final int c = counterInitialValue;
		counterInitialValue++;
		return c*c;
	}
}
