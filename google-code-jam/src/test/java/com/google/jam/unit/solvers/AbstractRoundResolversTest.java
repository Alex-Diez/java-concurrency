package com.google.jam.unit.solvers;

import java.util.Map;
import java.util.function.Function;

import com.google.jam.Round;
import com.google.jam.RoundFunctionFactory;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;
import com.google.jam.creators.RoundCreator;
import com.google.jam.solvers.RoundResolver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

abstract class AbstractRoundResolversTest {

    private final Function<String, Integer> algorithm;
    private final String location;
    private final char roundLetter;
    private final String complexity;
    private final String roundType;
    private final TestDataProvider testDataProvider;
    private final RoundFunctionFactory roundFunctionFactory;

    public AbstractRoundResolversTest(
            final Function<String, Integer> algorithm,
            final char roundLetter,
            final String location,
            final String complexity,
            final String roundType) {
        this.algorithm = algorithm;
        this.roundLetter = roundLetter;
        this.location = location;
        this.complexity = complexity;
        this.roundType = roundType;
        this.testDataProvider = new TestDataProvider();
        this.roundFunctionFactory = new RoundFunctionFactory();
    }

    private Round round;
    private RoundResolver roundResolver;

    @Before
    public void setUp()
            throws Exception {
        final RoundPathBuilder smokeTestPathBuilder = new RoundPathBuilder(
                location,
                roundLetter,
                complexity,
                roundType
        );
        final RoundCreator creator = new RoundCreator.Builder()
                .setRoundFunction(roundFunctionFactory.createRoundFunction(roundLetter))
                .build();
        round = new RoundTaskReader(smokeTestPathBuilder.build()).applyCreator(creator);
        roundResolver = createRoundResolver();
    }

    @After
    public void tearDown() throws Exception {
        roundResolver.shutDownResolver();
    }

    protected abstract RoundResolver createRoundResolver();

    @Test
    public void testTaskSolvingProcess()
            throws Exception {
        final Map<Integer, Integer> resolverResults = roundResolver.solve(round, algorithm);
        final Map<Integer, Integer> testData = testDataProvider.provideSetOfTestData(
                roundLetter,
                complexity,
                roundType
        );
        assertThat(
                "[" + algorithm.getClass().getSimpleName() + "] {" + roundResolver.getClass().getSimpleName() + "} " +
                        String.valueOf(roundLetter) + '-' + complexity + '-' + roundType,
                resolverResults,
                is(testData)
        );
    }
}
