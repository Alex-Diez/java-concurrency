package com.google.jam.unit.solvers;

import java.util.Map;
import java.util.function.Function;

import com.google.jam.Round;
import com.google.jam.RoundCreator;
import com.google.jam.RoundPathBuilder;
import com.google.jam.RoundTaskReader;
import com.google.jam.solvers.RoundResolver;

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
    private final RoundCreatorProvider roundCreatorProvider;

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
        this.roundCreatorProvider = new RoundCreatorProvider();
    }

    private Round round;

    @Before
    public void setUp()
            throws Exception {
        final RoundCreator creator = roundCreatorProvider.buildRoundCreator(roundLetter);
        final RoundPathBuilder smokeTestPathBuilder = new RoundPathBuilder(
                location,
                roundLetter,
                complexity,
                roundType
        );
        round = new RoundTaskReader(smokeTestPathBuilder.build()).applyCreator(creator);
    }

    @Test
    public void testTaskSolvingProcess()
            throws Exception {
        final Map<Integer, Integer> resolverResults = getResolver().solve(round, algorithm);
        final Map<Integer, Integer> testData = testDataProvider.provideSetOfTestData(roundLetter, complexity, roundType);
        assertThat(
                "[" + algorithm.getClass().getSimpleName() + "] {" + getResolver().getClass().getSimpleName() + "} " +
                        String.valueOf(roundLetter) + '-' + complexity + '-' + roundType,
                resolverResults,
                is(testData)
        );
    }

    protected abstract RoundResolver getResolver();
}
