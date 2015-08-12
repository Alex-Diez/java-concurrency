package com.google.jam.unit.creators;

import com.google.jam.WrongRoundFormatException;
import com.google.jam.creators.RoundCreator;
import com.google.jam.creators.RoundFunctionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@RunWith(Parameterized.class)
public class RoundCreatorWrongInputDataTest {

    @Parameters
    public static Collection<Object[]> data() {
        return new DataProvider().provide(
                new RoundLetterSupplier(),
                new TaskQueueWrongLengthSupplier(),
                new RoundCorrectInputTestDataSupplier()
        );
    }

    private final List<String> testData;
    private final Function<List<String>, Map<Integer, String>> roundFunction;

    public RoundCreatorWrongInputDataTest(final char roundLetter, final List<String> testData) {
        this.testData = testData;
        this.roundFunction = new RoundFunctionFactory().createRoundFunction(roundLetter);
    }

    private RoundCreator roundCreator;

    @Before
    public void setUp()
            throws Exception {
        roundCreator = new RoundCreator();
    }


    @Test(expected = WrongRoundFormatException.class)
    public void testWrongStandingOvationRoundFormat_shouldThrowException()
            throws Exception {
        roundCreator.createRound(testData, roundFunction);
    }

    @Test(expected = WrongRoundFormatException.class)
    public void testWrongStandingOvationRoundFormatMultiThread_shouldThrowException()
            throws Exception {
        roundCreator.createRoundForMultiThreadEnvironment(testData, roundFunction);
    }

    static class DataProvider {
        public Collection<Object[]> provide(
                final Supplier<Iterator<Character>> roundLetterSupplier,
                final Supplier<Iterator<String>> taskQueueLengthSupplier,
                final Supplier<Iterator<List<String>>> roundInputTestDataSupplier) {
            final Collection<Object[]> collection = new ArrayList<>();
            final Iterator<Character> roundLetterIterator = roundLetterSupplier.get();
            final Iterator<List<String>> roundInputTestDataIterator = roundInputTestDataSupplier.get();
            final Iterator<String> taskQueueLengthIterator = taskQueueLengthSupplier.get();
            while (roundInputTestDataIterator.hasNext()) {
                char roundLetter = roundLetterIterator.next();
                final List<String> roundInputTestDataNext = roundInputTestDataIterator.next();
                while (taskQueueLengthIterator.hasNext()) {
                    final List<String> roundInputTestData = new ArrayList<>(roundInputTestDataNext);
                    final String taskQueueLength = taskQueueLengthIterator.next();
                    roundInputTestData.add(0, taskQueueLength);
                    collection.add(new Object[] {roundLetter, roundInputTestData});
                }
            }
            return collection;
        }
    }
}
