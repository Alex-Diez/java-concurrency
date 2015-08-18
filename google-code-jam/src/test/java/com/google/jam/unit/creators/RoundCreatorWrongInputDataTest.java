package com.google.jam.unit.creators;

import com.google.jam.WrongRoundFormatException;
import com.google.jam.creators.RoundCreator;
import com.google.jam.RoundFunctionFactory;
import com.google.jam.datastructures.LastIndexTaskQueue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@RunWith(Parameterized.class)
public class RoundCreatorWrongInputDataTest {

    @Parameters
    public static Collection<Object[]> data() {
        return new DataProvider().provide(
                new ThreadEnvironmentFunctionSupplier(),
                new RoundLetterSupplier(),
                new TaskQueueWrongLengthSupplier(),
                new RoundCorrectInputTestDataSupplier()
        );
    }

    private final Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction;
    private final List<String> testData;
    private final Function<List<String>, Collection<String>> roundFunction;

    public RoundCreatorWrongInputDataTest(
            final Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction,
            final char roundLetter,
            final List<String> testData) {
        this.threadEnvironmentFunction = threadEnvironmentFunction;
        this.testData = testData;
        this.roundFunction = new RoundFunctionFactory().createRoundFunction(roundLetter);
    }

    private RoundCreator roundCreator;

    @Before
    public void setUp()
            throws Exception {
        roundCreator = new RoundCreator.Builder(threadEnvironmentFunction).setRoundFunction(roundFunction).build();
    }


    @Test(expected = WrongRoundFormatException.class)
    public void testWrongStandingOvationRoundFormat_shouldThrowException()
            throws Exception {
        roundCreator.createRound(testData);
    }

    static class DataProvider {
        public Collection<Object[]> provide(
                final Supplier<Iterator<Function<Collection<String>, LastIndexTaskQueue<String>>>> threadEnvironmentFunctionSupplier,
                final Supplier<Iterator<Character>> roundLetterSupplier,
                final Supplier<Iterator<String>> taskQueueLengthSupplier,
                final Supplier<Iterator<List<String>>> roundInputTestDataSupplier) {
            final Collection<Object[]> collection = new ArrayList<>();
            final Iterator<Function<Collection<String>, LastIndexTaskQueue<String>>>
                    threadEnvironmentFunctionIterator = threadEnvironmentFunctionSupplier.get();
            while (threadEnvironmentFunctionIterator.hasNext()) {
                Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction =
                        threadEnvironmentFunctionIterator.next();
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
                        collection.add(new Object[] {threadEnvironmentFunction, roundLetter, roundInputTestData});
                    }
                }
            }
            return collection;
        }
    }
}
