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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

abstract class AbstractRoundResolversTest {

    private final Function<String, Integer> algorithm;
    private final String smokeTestLocation;
    private final char roundLetter;
    private final String smokeTestComplexity;
    private final String smokeTestRoundType;
    private final String smallLocation;
    private final String smallComplexity;
    private final String smallRoundType;
    private final String largeLocation;
    private final String largeComplexity;
    private final String largeRoundType;
    private final TestDataProvider testDataProvider;
    private final RoundCreatorProvider roundCreatorProvider;

    public AbstractRoundResolversTest(
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
            final String largeRoundType) {
        this.algorithm = algorithm;
        this.roundLetter = roundLetter;
        this.smokeTestLocation = smokeTestLocation;
        this.smokeTestComplexity = smokeTestComplexity;
        this.smokeTestRoundType = smokeTestRoundType;
        this.smallLocation = smallLocation;
        this.smallComplexity = smallComplexity;
        this.smallRoundType = smallRoundType;
        this.largeLocation = largeLocation;
        this.largeComplexity = largeComplexity;
        this.largeRoundType = largeRoundType;
        this.testDataProvider = new TestDataProviderFactory().createDataProvider(roundLetter);
        this.roundCreatorProvider = new RoundCreatorProvider();
    }

    private Round smokeTestRound;
    private Round smallTaskRound;
    private Round largeTaskRound;

    @Before
    public void setUp()
            throws Exception {
        final RoundCreator creator = roundCreatorProvider.buildRoundCreator(roundLetter);
        final RoundPathBuilder smokeTestPathBuilder = new RoundPathBuilder(
                smokeTestLocation,
                roundLetter,
                smokeTestComplexity,
                smokeTestRoundType
        );
        smokeTestRound = new RoundTaskReader(smokeTestPathBuilder.build()).applyCreator(creator);
        final RoundPathBuilder smallPathBuilder = new RoundPathBuilder(
                smallLocation,
                roundLetter,
                smallComplexity,
                smallRoundType
        );
        smallTaskRound = new RoundTaskReader(smallPathBuilder.build()).applyCreator(creator);
        final RoundPathBuilder largePathBuilder = new RoundPathBuilder(
                largeLocation,
                roundLetter,
                largeComplexity,
                largeRoundType
        );
        largeTaskRound = new RoundTaskReader(largePathBuilder.build()).applyCreator(creator);
    }

    @Test
    public void testTaskSolvingProcess()
            throws Exception {
        checkResolverResults(
                getResolver().solve(smokeTestRound, algorithm),
                testDataProvider.provideSmallSetOfTestData());
    }

    @Test
    public void testSmallTaskSolvingProcess()
            throws Exception {
        checkResolverResults(
                getResolver().solve(smallTaskRound, algorithm),
                testDataProvider.provideSmallSetOfPracticeData()
        );
    }

    @Test
    public void testLargeTaskSolvingProcess()
            throws Exception {
        checkResolverResults(
                getResolver().solve(largeTaskRound, algorithm),
                testDataProvider.provideLargeSetOfPracticeData()
        );
    }

    private void checkResolverResults(Map<Integer, Integer> resolverResults, Map<Integer, Integer> testData) {
        assertThat(
                "[" + algorithm.getClass().getSimpleName() + "] {" + getResolver().getClass().getSimpleName() + "}",
                resolverResults,
                is(testData)
        );
    }

    protected abstract RoundResolver getResolver();
}
