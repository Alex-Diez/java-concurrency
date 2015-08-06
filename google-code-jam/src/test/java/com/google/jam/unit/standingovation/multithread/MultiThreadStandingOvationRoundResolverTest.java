package com.google.jam.unit.standingovation.multithread;

import java.util.function.Function;

import com.google.jam.MultiThreadRoundResolver;
import com.google.jam.RoundCreator;
import com.google.jam.RoundResolver;
import com.google.jam.standingovation.StandingOvationRoundCreator;
import com.google.jam.standingovation.multithread.MultiThreadStandingOvationRoundResolver;

import org.junit.After;
import org.junit.Before;

public class MultiThreadStandingOvationRoundResolverTest
        /*extends AbstractStandingOvationRoundResolverTest*/ {

    private RoundCreator creator;
    private MultiThreadRoundResolver resolver;

    public MultiThreadStandingOvationRoundResolverTest(Function<String, Integer> algorithm) {
//		super(algorithm);
    }

    @Before
    public void setUp()
            throws Exception {
        resolver = new MultiThreadStandingOvationRoundResolver();
        creator = new StandingOvationRoundCreator(true);
    }

    @After
    public void tearDown()
            throws Exception {
        resolver.shutdownThreadPool();
    }

    //	@Override
    public RoundResolver getResolver() {
        return resolver;
    }

    //	@Override
    public RoundCreator getCreator() {
        return creator;
    }
}
