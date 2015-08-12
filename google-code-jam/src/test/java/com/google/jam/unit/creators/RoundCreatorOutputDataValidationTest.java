package com.google.jam.unit.creators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import com.google.jam.Round;
import com.google.jam.RoundResolutionFactory;
import com.google.jam.creators.RoundCreator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class RoundCreatorOutputDataValidationTest {

    @Parameters
    public static Collection<Object[]> data() {
        return new DataProvider().provide(
                new RoundResolutionFactoriesSupplier(),
                new TaskQueueRightLengthSupplier(),
                new RoundCorrectInputTestDataSupplier(),
                new RegularExpressionSupplier()
        );
    }

    private final RoundResolutionFactory roundResolutionFactory;
    private final List<String> testData;
    private final String pattern;

    public RoundCreatorOutputDataValidationTest(
            RoundResolutionFactory roundResolutionFactory,
            List<String> testData,
            String pattern) {
        this.roundResolutionFactory = roundResolutionFactory;
        this.testData = testData;
        this.pattern = pattern;
    }

    private RoundCreator roundCreator;

    @Before
    public void setUp()
            throws Exception {
        roundCreator = roundResolutionFactory.buildRoundCreator();
    }

    @Test
    public void testValidateRound()
            throws Exception {
        final Round round = roundCreator.createRound(new ArrayList<>(testData));
        while (round.hasNextTask()) {
            String task = round.getNextTask().getValue();
            assertThat(task, matchesPattern(pattern));
        }
    }

    @Test
    public void testValidateRoundMultiThread()
            throws Exception {
        final Round round = roundCreator.createRoundForMultiThreadEnvironment(new ArrayList<>(testData));
        while (round.hasNextTask()) {
            String task = round.getNextTask().getValue();
            assertThat(task, matchesPattern(pattern));
        }
    }

    static class DataProvider {
        public Collection<Object[]> provide(
                final Supplier<Iterator<RoundResolutionFactory>> roundResolutionFactorySupplier,
                final Supplier<Iterator<String>> taskQueueLengthSupplier,
                final Supplier<Iterator<List<String>>> roundInputTestDataSupplier,
                final Supplier<Iterator<String>> regularExpressionSupplier) {
            final Collection<Object[]> collection = new ArrayList<>();
            final Iterator<RoundResolutionFactory> roundResolutionFactoryIterator = roundResolutionFactorySupplier.get();
            final Iterator<List<String>> roundInputTestDataIterator = roundInputTestDataSupplier.get();
            final Iterator<String> regularExpressionIterator = regularExpressionSupplier.get();
            while (roundResolutionFactoryIterator.hasNext()) {
                final RoundResolutionFactory roundResolutionFactory = roundResolutionFactoryIterator.next();
                final String regularExpression = regularExpressionIterator.next();
                final List<String> roundInputTestDataNext = roundInputTestDataIterator.next();
                final Iterator<String> taskQueueLengthIterator = taskQueueLengthSupplier.get();
                while (taskQueueLengthIterator.hasNext()) {
                    final List<String> roundInputTestData = new ArrayList<>(roundInputTestDataNext);
                    final String taskQueueLength = taskQueueLengthIterator.next();
                    roundInputTestData.add(0, taskQueueLength);
                    collection.add(new Object[] {roundResolutionFactory, roundInputTestData, regularExpression});
                }
            }
            return collection;
        }
    }
}
