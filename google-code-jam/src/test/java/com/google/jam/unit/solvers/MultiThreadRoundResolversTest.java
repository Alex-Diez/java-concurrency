package com.google.jam.unit.solvers;

import com.google.jam.creators.MultiThreadEnvironmentFunction;
import com.google.jam.datastructures.LastIndexTaskQueue;
import com.google.jam.solvers.MultiThreadRoundResolver;
import com.google.jam.solvers.RoundResolver;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

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

    private final Supplier<Integer> numberOfThreadFunction;

    public MultiThreadRoundResolversTest(
            final Function<String, Integer> algorithm,
            final char roundLetter,
            final String location,
            final String complexity,
            final String roundType,
            final Supplier<Integer> numberOfThreadFunction) {
        super(algorithm, roundLetter, location, complexity, roundType);
        this.numberOfThreadFunction = numberOfThreadFunction;
    }

    @Override
    protected RoundResolver createRoundResolver() {
        return new MultiThreadRoundResolver(numberOfThreadFunction);
    }

    @Override
    protected Function<Map<Integer, String>, LastIndexTaskQueue<String>> createThreadEnvironmentFactory() {
        return new MultiThreadEnvironmentFunction();
    }

    private static class DataProvider {

        public Collection<Object[]> provide(
                final AlgorithmSupplier algorithmSupplier,
                final Supplier<Iterator<Character>> roundLetterSupplier,
                final Supplier<Iterator<String[]>> testDataLocationSupplier,
                final Supplier<Iterator<Supplier<Integer>>> numberOfThreadFunctionSupplier) {
            final Collection<Object[]> collection = new ArrayList<>();
            final Iterator<Character> roundLetterIterator = roundLetterSupplier.get();
            while (roundLetterIterator.hasNext()) {
                final Character roundLetter = roundLetterIterator.next();
                final Iterator<Function<String, Integer>> algorithmIterator = algorithmSupplier.get(roundLetter);
                while (algorithmIterator.hasNext()) {
                    final Function<String, Integer> algorithm = algorithmIterator.next();
                    final Iterator<String[]> testDataLocationIterator = testDataLocationSupplier.get();
                    while (testDataLocationIterator.hasNext()) {
                        final String[] testDataLocation = testDataLocationIterator.next();
                        final Iterator<Supplier<Integer>> numberOfThreadFunctionIterator =
                                numberOfThreadFunctionSupplier.get();
                        while (numberOfThreadFunctionIterator.hasNext()) {
                            final Supplier<Integer> numberOfThreadFunction = numberOfThreadFunctionIterator.next();
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
