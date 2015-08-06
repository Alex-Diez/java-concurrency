package org.fairytale.matchers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.hamcrest.Description;

public class RelativeStarvationThreadMatcher<R extends Runnable, C extends Number>
        extends AbstractStarvationThreadMatcher<R, C> {

    private final float drawbackPercent;
    private long minExpectedNumbersOfInvoking;
    private long maxExpectedNumbersOfInvoking;

    public RelativeStarvationThreadMatcher(Set<R> runnables, float drawbackPercent) {
        super(runnables);
        this.drawbackPercent = drawbackPercent;
    }

    @Override
    protected void prepareConditions(Map<R, C> invokedStatistic) {
        final long maxInvokingTime = findMaxInvokeTime(invokedStatistic);
        calculateMinExpectedNumbersOfInvoking(maxInvokingTime);
        calculateMaxExpectedNumbersOfInvoking(maxInvokingTime);
    }

    @Override
    protected void generateDescription(Description description, Iterator<R> iterator) {
        description.appendValue(iterator.next())
                .appendText(" has to be invoked between")
                .appendValue(minExpectedNumbersOfInvoking)
                .appendText(" and ")
                .appendValue(maxExpectedNumbersOfInvoking)
                .appendText(" times");
    }

    @Override
    protected boolean isInCondition(final long invokingTime) {
        return minExpectedNumbersOfInvoking <= invokingTime
                && invokingTime <= maxExpectedNumbersOfInvoking;
    }

    private long findMaxInvokeTime(Map<R, C> statistics) {
        final Collection<C> invokingCounters = statistics.values();
        long max = 0;
        for (C counter : invokingCounters) {
            final long v = counter.longValue();
            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    private void calculateMinExpectedNumbersOfInvoking(final long maxInvokingTime) {
        this.minExpectedNumbersOfInvoking = (long) (maxInvokingTime - (drawbackPercent * maxInvokingTime));
    }

    private void calculateMaxExpectedNumbersOfInvoking(final long maxInvokingTime) {
        this.maxExpectedNumbersOfInvoking = (long) (maxInvokingTime + (drawbackPercent * maxInvokingTime) + 1);
    }
}
