package com.google.jam.unit.solvers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.jam.solvers.RoundResolver;
import com.google.jam.solvers.SingleThreadRoundResolver;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SingleThreadRoundResolversTest
        extends AbstractRoundResolversTest {

    @Parameters
    public static Collection<Object[]> dataSubClass() {
        return new DataProvider().provide(
                new AlgorithmSupplier(),
                new RoundLetterSupplier(),
                new TestDataLocationSupplier()
        );
    }

    public SingleThreadRoundResolversTest(
            Function<String, String> algorithm,
            char roundLetter,
            String smokeTestLocation,
            String smokeTestComplexity,
            String roundType) {
        super(algorithm, roundLetter, smokeTestLocation, smokeTestComplexity, roundType);
    }

    @Override
    protected RoundResolver createRoundResolver() {
        return new SingleThreadRoundResolver();
    }

    private static class DataProvider {

        public Collection<Object[]> provide(
                final AlgorithmSupplier algorithmSupplier,
                final Supplier<Iterator<Character>> roundLetterSupplier,
                final Supplier<Iterator<String[]>> testDataLocationSupplier) {
            Collection<Object[]> collection = new ArrayList<>();
            Iterator<Character> roundLetterIterator = roundLetterSupplier.get();
            while (roundLetterIterator.hasNext()) {
                Character roundLetter = roundLetterIterator.next();
                Iterator<Function<String, String>> algorithmIterator = algorithmSupplier.get(roundLetter);
                while (algorithmIterator.hasNext()) {
                    Function<String, String> algorithm = algorithmIterator.next();
                    Iterator<String[]> testDataLocationIterator = testDataLocationSupplier.get();
                    while (testDataLocationIterator.hasNext()) {
                        String[] testDataLocation = testDataLocationIterator.next();
                        collection.add(
                                new Object[] {
                                        algorithm,
                                        roundLetter,
                                        testDataLocation[0],
                                        testDataLocation[1],
                                        testDataLocation[2]
                                }
                        );
                    }
                }
            }
            return collection;
        }
    }
}
