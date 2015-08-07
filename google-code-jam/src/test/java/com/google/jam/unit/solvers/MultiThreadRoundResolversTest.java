package com.google.jam.unit.solvers;

import java.util.function.Function;

import com.google.jam.solvers.MultiThreadRoundResolver;
import com.google.jam.solvers.RoundResolver;

import org.junit.After;
import org.junit.Before;

public class MultiThreadRoundResolversTest
        extends AbstractRoundResolversTest {

    public MultiThreadRoundResolversTest(
            Function<String, Integer> algorithm,
            char roundLetter,
            String smokeTestLocation,
            String smokeTestComplexity,
            String smokeTestRoundType,
            String smallLocation,
            String smallComplexity,
            String smallRoundType,
            String largeLocation,
            String largeComplexity,
            String largeRoundType) {
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
                largeRoundType);
    }

    private MultiThreadRoundResolver resolver;

    @Override
    @Before
    public void setUp()
            throws Exception {
        super.setUp();
        resolver = new MultiThreadRoundResolver();
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
}
