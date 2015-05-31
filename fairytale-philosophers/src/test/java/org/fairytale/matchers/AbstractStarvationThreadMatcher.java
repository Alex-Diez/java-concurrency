package org.fairytale.matchers;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public abstract class AbstractStarvationThreadMatcher<R extends Runnable, C extends Number>
		extends TypeSafeDiagnosingMatcher<Map<R, C>> {

	protected final Set<R> runnables;

	protected AbstractStarvationThreadMatcher(Set<R> runnables) {
		this.runnables = runnables;
	}

	@Override
	protected boolean matchesSafely(Map<R, C> invokedStatistic, Description mismatchDescription) {
		prepareConditions(invokedStatistic);
		boolean result = true;
		Iterator<R> iterator = runnables.iterator();
		while (iterator.hasNext()) {
			final R runnable = iterator.next();
			final long actualNumberOfInvoking = getActualNumberOfInvoking(invokedStatistic, runnable);
			result &= isInCondition(actualNumberOfInvoking);
			generateMismatchDescription(mismatchDescription, runnable, actualNumberOfInvoking);
			appendNextFormattedLine(mismatchDescription, iterator);
		}
		return result;
	}

	@Override
	public void describeTo(Description description) {
		Iterator<R> iterator = runnables.iterator();
		while (iterator.hasNext()) {
			generateDescription(description, iterator);
			appendNextFormattedLine(description, iterator);
		}
	}

	protected abstract void generateDescription(Description description, Iterator<R> iterator);

	protected abstract boolean isInCondition(final long invokingTime);

	protected abstract void prepareConditions(Map<R, C> statistics);

	private void appendNextFormattedLine(Description mismatchDescription, Iterator<R> iterator) {
		if (iterator.hasNext()) {
			mismatchDescription.appendText("\n          ");
		}
	}

	private void generateMismatchDescription(Description mismatchDescription, R runnable, long actualNumberOfInvoking) {
		mismatchDescription.appendValue(runnable)
				.appendText(" was invoked ")
				.appendValue(actualNumberOfInvoking)
				.appendText(" times");
	}

	private long getActualNumberOfInvoking(Map<R, C> invokedStatistic, R runnable) {
		return hasRunnableStatistic(invokedStatistic, runnable)
				? invokedStatistic.get(runnable).longValue()
				: 0;
	}

	private boolean hasRunnableStatistic(Map<R, C> invokedStatistic, R runnable) {
		return invokedStatistic.containsKey(runnable);
	}

}
