package com.google.jam.unit.solvers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

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
            final String location,
            final String complexity,
            final String roundType,
            final Function<Void, Integer> numberOfThreadFunction) {
        super(algorithm, roundLetter, location, complexity, roundType);
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
                final AlgorithmSupplier algorithmSupplier,
                final Supplier<Iterator<Character>> roundLetterSupplier,
                final Supplier<Iterator<String[]>> testDataLocationSupplier,
                final Supplier<Iterator<Function<Void, Integer>>> numberOfThreadFunctionSupplier) {
            Collection<Object[]> collection = new ArrayList<>();
            Iterator<Character> roundLetterIterator = roundLetterSupplier.get();
            while (roundLetterIterator.hasNext()) {
                Character roundLetter = roundLetterIterator.next();
                Iterator<Function<String, Integer>> algorithmIterator = algorithmSupplier.get(roundLetter);
                while (algorithmIterator.hasNext()) {
                    Function<String, Integer> algorithm = algorithmIterator.next();
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
