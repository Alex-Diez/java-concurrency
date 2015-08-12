package com.google.jam.unit.creators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import com.google.jam.RoundResolutionFactory;
import com.google.jam.WrongRoundFormatException;
import com.google.jam.creators.RoundCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RoundCreatorWrongInputDataTest {

    @Parameters
    public static Collection<Object[]> data() {
        return new DataProvider().provide(
                new RoundResolutionFactoriesSupplier(),
                new TaskQueueWrongLengthSupplier(),
                new RoundCorrectInputTestDataSupplier()
        );
    }

    private final RoundResolutionFactory roundResolutionFactory;
    private final List<String> testData;

    public RoundCreatorWrongInputDataTest(
            RoundResolutionFactory roundResolutionFactory,
            List<String> testData) {
        this.roundResolutionFactory = roundResolutionFactory;
        this.testData = testData;
    }

    private RoundCreator roundCreator;

    @Before
    public void setUp()
            throws Exception {
        roundCreator = roundResolutionFactory.buildRoundCreator();
    }


    @Test(expected = WrongRoundFormatException.class)
    public void testWrongStandingOvationRoundFormat_shouldThrowException()
            throws Exception {
        roundCreator.createRound(testData);
    }

    @Test(expected = WrongRoundFormatException.class)
    public void testWrongStandingOvationRoundFormatMultiThread_shouldThrowException()
            throws Exception {
        roundCreator.createRoundForMultiThreadEnvironment(testData);
    }

    static class DataProvider {
        public Collection<Object[]> provide(
                final Supplier<Iterator<RoundResolutionFactory>> roundResolutionFactorySupplier,
                final Supplier<Iterator<String>> taskQueueLengthSupplier,
                final Supplier<Iterator<List<String>>> roundInputTestDataSupplier) {
            final Collection<Object[]> collection = new ArrayList<>();
            final Iterator<RoundResolutionFactory> roundResolutionFactoryIterator = roundResolutionFactorySupplier.get();
            final Iterator<List<String>> roundInputTestDataIterator = roundInputTestDataSupplier.get();
            final Iterator<String> taskQueueLengthIterator = taskQueueLengthSupplier.get();
            while (roundResolutionFactoryIterator.hasNext()) {
                final RoundResolutionFactory roundResolutionFactory = roundResolutionFactoryIterator.next();
                final List<String> roundInputTestDataNext = roundInputTestDataIterator.next();
                while (taskQueueLengthIterator.hasNext()) {
                    final List<String> roundInputTestData = new ArrayList<>(roundInputTestDataNext);
                    final String taskQueueLength = taskQueueLengthIterator.next();
                    roundInputTestData.add(0, taskQueueLength);
                    collection.add(new Object[] {roundResolutionFactory, roundInputTestData});
                }
            }
            return collection;
        }
    }
}
