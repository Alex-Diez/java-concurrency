package org.fairytale.matchers;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.hamcrest.Description;

public class DirectStarvationThreadMatcher<R extends Runnable, C extends Number>
		extends AbstractStarvationThreadMatcher<R, C> {

	private final long directNumbersOfInvoking;

	public DirectStarvationThreadMatcher(Set<R> runnables, long directNumbersOfInvoking) {
		super(runnables);
		this.directNumbersOfInvoking = directNumbersOfInvoking;
	}

	@Override
	protected void prepareConditions(Map<R, C> statistics) {
	}

	@Override
	protected void generateDescription(Description description, Iterator<R> iterator) {
		description.appendValue(iterator.next())
				.appendText(" has to be invoked ")
				.appendValue(directNumbersOfInvoking)
				.appendText(" times");
	}

	@Override
	protected boolean isInCondition(final long invokingTime) {
		return invokingTime == directNumbersOfInvoking;
	}
}
