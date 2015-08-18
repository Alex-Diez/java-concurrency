package com.google.jam.unit.creators;

import com.google.jam.Round;
import com.google.jam.creators.RoundCreator;
import com.google.jam.RoundFunctionFactory;
import com.google.jam.datastructures.LastIndexTaskQueue;
import org.junit.Ignore;
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

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class RoundCreatorOutputDataValidationTest {

    @Parameters
    public static Collection<Object[]> data() {
        return new DataProvider().provide(
                new ThreadEnvironmentFunctionSupplier(),
                new RoundLetterSupplier(),
                new TaskQueueRightLengthSupplier(),
                new RoundCorrectInputTestDataSupplier(),
                new RegularExpressionSupplier()
        );
    }

    private final Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction;
    private final List<String> testData;
    private final String pattern;
    private final RoundCreator roundCreator;
    private final Function<List<String>, Collection<String>> roundFunction;

    public RoundCreatorOutputDataValidationTest(
            final Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction,
            final char roundLetter,
            final List<String> testData,
            final String pattern) {
        this.threadEnvironmentFunction = threadEnvironmentFunction;
        this.testData = testData;
        this.pattern = pattern;
        this.roundCreator = new RoundCreator();
        this.roundFunction = new RoundFunctionFactory().createRoundFunction(roundLetter);
    }

    @Test
    public void testValidateRound()
            throws Exception {
        final Round round = roundCreator.createRound(
                new ArrayList<>(testData),
                roundFunction,
                threadEnvironmentFunction
        );
        while (round.hasNextTask()) {
            final String task = round.getNextTask();
            assertThat(task, matchesPattern(pattern));
        }
    }

    @Test
    @Ignore("Develop queues")
    public void testRoundLastTaskId() throws Exception {
        final Round round = roundCreator.createRound(
                new ArrayList<>(testData),
                roundFunction,
                threadEnvironmentFunction
        );
        while (round.hasNextTask()) {
            final int index = round.getLastTaskId();
            assertThat(
                    "[ " + roundFunction.getClass().getSimpleName() + " ] [ " + threadEnvironmentFunction.getClass().getSimpleName() + " ]",
                    index,
                    is(round.numberOfTasks())
            );
            round.getNextTask();
        }
    }

    static class DataProvider {
        public Collection<Object[]> provide(
                final Supplier<Iterator<Function<Collection<String>, LastIndexTaskQueue<String>>>> threadEnvironmentFunctionSupplier,
                final Supplier<Iterator<Character>> roundLetterSupplier,
                final Supplier<Iterator<String>> taskQueueLengthSupplier,
                final Supplier<Iterator<List<String>>> roundInputTestDataSupplier,
                final Supplier<Iterator<String>> regularExpressionSupplier) {
            final Collection<Object[]> collection = new ArrayList<>();
            final Iterator<Function<Collection<String>, LastIndexTaskQueue<String>>>
                    threadEnvironmentFunctionIterator = threadEnvironmentFunctionSupplier.get();
            while (threadEnvironmentFunctionIterator.hasNext()) {
                Function<Collection<String>, LastIndexTaskQueue<String>> threadEnvironmentFunction =
                        threadEnvironmentFunctionIterator.next();
                final Iterator<Character> roundLetterIterator = roundLetterSupplier.get();
                final Iterator<List<String>> roundInputTestDataIterator = roundInputTestDataSupplier.get();
                final Iterator<String> regularExpressionIterator = regularExpressionSupplier.get();
                while (roundInputTestDataIterator.hasNext()) {
                    final Character roundLetter = roundLetterIterator.next();
                    final String regularExpression = regularExpressionIterator.next();
                    final List<String> roundInputTestDataNext = roundInputTestDataIterator.next();
                    final Iterator<String> taskQueueLengthIterator = taskQueueLengthSupplier.get();
                    while (taskQueueLengthIterator.hasNext()) {
                        final List<String> roundInputTestData = new ArrayList<>(roundInputTestDataNext);
                        final String taskQueueLength = taskQueueLengthIterator.next();
                        roundInputTestData.add(0, taskQueueLength);
                        collection.add(
                                new Object[] {
                                        threadEnvironmentFunction,
                                        roundLetter,
                                        roundInputTestData,
                                        regularExpression
                                }
                        );
                    }
                }
            }
            return collection;
        }
    }
}
