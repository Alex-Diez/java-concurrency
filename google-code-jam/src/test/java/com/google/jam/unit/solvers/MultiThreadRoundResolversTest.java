package com.google.jam.unit.solvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;
import com.google.jam.experiments.CPUNumberOfThreadFunction;
import com.google.jam.experiments.DoubleCPUNumberOfThreadFunction;
import com.google.jam.solvers.MultiThreadRoundResolver;
import com.google.jam.solvers.RoundResolver;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MultiThreadRoundResolversTest
        extends AbstractRoundResolversTest {

    @Parameters
    public static Collection<Object[]> dataSubClass() {
        return new DataProvider().provide(
                new AlgorithmSupplier(),
                new RoundLetterSupplier(),
                new TestDataLocationSupplier(),
                new NumberOfThreadFunctionSupplier()
        );
    }

    private final Function<Void, Integer> numberOfThreadFunction;

    public MultiThreadRoundResolversTest(
            final Function<String, Integer> algorithm,
            final char roundLetter,
            final String smokeTestLocation,
            final String smokeTestComplexity,
            final String smokeTestRoundType,
            final String smallLocation,
            final String smallComplexity,
            final String smallRoundType,
            final String largeLocation,
            final String largeComplexity,
            final String largeRoundType,
            final Function<Void, Integer> numberOfThreadFunction) {
        super(
                algorithm,
                roundLetter,
                smokeTestLocation,
                smokeTestComplexity,
                smokeTestRoundType,
                smallLocation,
                smallComplexity,
                smallRoundType,
                largeLocation,
                largeComplexity,
                largeRoundType
        );
        this.numberOfThreadFunction = numberOfThreadFunction;
    }

    private MultiThreadRoundResolver resolver;

    @Override
    @Before
    public void setUp()
            throws Exception {
        super.setUp();
        resolver = new MultiThreadRoundResolver(numberOfThreadFunction);
    }

    @Override
    protected RoundResolver getResolver() {
        return resolver;
    }

    @After
    public void tearDown()
            throws Exception {
        resolver.shutdownThreadPool();
    }

    private static class DataProvider {

        public Collection<Object[]> provide(
                final Supplier<Iterator<Function<String, Integer>>> algorithmSupplier,
                final Supplier<Iterator<Character>> roundLetterSupplier,
                final Supplier<Iterator<String[]>> testDataLocationSupplier,
                final Supplier<Iterator<Function<Void, Integer>>> numberOfThreadFunctionSupplier) {
            Collection<Object[]> collection = new ArrayList<>();
            Iterator<Function<String, Integer>> algorithmIterator = algorithmSupplier.get();
            while (algorithmIterator.hasNext()) {
                Function<String, Integer> algorithm = algorithmIterator.next();
                Iterator<Character> roundLetterIterator = roundLetterSupplier.get();
                while (roundLetterIterator.hasNext()) {
                    Character roundLetter = roundLetterIterator.next();
                    Iterator<String[]> testDataLocationIterator = testDataLocationSupplier.get();
                    while (testDataLocationIterator.hasNext()) {
                        String[] testDataLocation = testDataLocationIterator.next();
                        Iterator<Function<Void, Integer>> numberOfThreadFunctionIterator =
                                numberOfThreadFunctionSupplier.get();
                        while (numberOfThreadFunctionIterator.hasNext()) {
                            Function<Void, Integer> numberOfThreadFunction = numberOfThreadFunctionIterator.next();
                            collection.add(
                                    new Object[] {
                                            algorithm,
                                            roundLetter,
                                            testDataLocation[0],
                                            testDataLocation[1],
                                            testDataLocation[2],
                                            testDataLocation[3],
                                            testDataLocation[4],
                                            testDataLocation[5],
                                            testDataLocation[6],
                                            testDataLocation[7],
                                            testDataLocation[8],
                                            numberOfThreadFunction
                                    }
                            );
                        }
                    }
                }
            }
            return collection;
        }
    }
}
