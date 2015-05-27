package org.fairytale.matchers;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.fairytale.Philosopher;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class MapElements<Ph extends Philosopher, C extends Number>
		extends TypeSafeDiagnosingMatcher<Map<Ph, C>> {

	private final Set<Ph> keys;
	private final long expectedValue;

	public MapElements(Set<Ph> keys, long expectedValue) {
		this.keys = keys;
		this.expectedValue = expectedValue;
	}

	@Override
	protected boolean matchesSafely(Map<Ph, C> item, Description mismatchDescription) {
		final Set<Ph> mapsKey = item.keySet();
		boolean result = true;
		for(Ph key : keys) {
			if(!mapsKey.contains(key)) {
				mismatchDescription.appendValue(key)
						.appendText(" has eaten ")
						.appendValue(0)
						.appendText("\n          ");
				result = false;
			}
			else {
				C mapValue = item.get(key);
				long actualValue = mapValue.longValue();
				result &= (expectedValue == actualValue);
				mismatchDescription.appendValue(key)
						.appendText(" has eaten ")
						.appendValue(actualValue)
						.appendText("\n          ");
			}
		}
		return result;
	}

	@Override
	public void describeTo(Description description) {
		Iterator<Ph> iterator = keys.iterator();
		while(iterator.hasNext()) {
			description.appendValue(iterator.next())
					.appendText(" has eaten ")
					.appendValue(expectedValue);
			if(iterator.hasNext()) {
				description.appendText("\n          ");
			}
		}
	}
}
