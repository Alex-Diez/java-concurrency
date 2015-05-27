package org.fairytale.matchers;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class MapElements<R extends Runnable, C extends Number>
		extends TypeSafeDiagnosingMatcher<Map<R, C>> {

	private final Set<R> runnables;
	private final long expectedNumbersOfInvoking;

	public MapElements(Set<R> runnables, long expectedNumbersOfInvoking) {
		this.runnables = runnables;
		this.expectedNumbersOfInvoking = expectedNumbersOfInvoking;
	}

	@Override
	protected boolean matchesSafely(Map<R, C> invokedStatistic, Description mismatchDescription) {
		boolean result = true;
		for (R runnable : runnables) {
			final long actualNumberOfInvoking = invokedStatistic.containsKey(runnable)
					? invokedStatistic.get(runnable).longValue()
					: 0;
			result &= (expectedNumbersOfInvoking == actualNumberOfInvoking);
			mismatchDescription.appendValue(runnable)
					.appendText(" was invoked ")
					.appendValue(actualNumberOfInvoking)
					.appendText(" times")
					.appendText("\n          ");
		}
		return result;
	}

	@Override
	public void describeTo(Description description) {
		Iterator<R> iterator = runnables.iterator();
		while (iterator.hasNext()) {
			description.appendValue(iterator.next())
					.appendText(" has to be invoked ")
					.appendValue(expectedNumbersOfInvoking)
					.appendText(" times");
			if (iterator.hasNext()) {
				description.appendText("\n          ");
			}
		}
	}
}
