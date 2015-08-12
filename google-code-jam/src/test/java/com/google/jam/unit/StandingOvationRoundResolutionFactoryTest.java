package com.google.jam.unit;

import com.google.jam.RoundResolutionFactory;
import com.google.jam.StandingOvationRoundResolutionFactory;
import com.google.jam.creators.RoundCreator;
import com.google.jam.creators.StandingOvationRoundCreator;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class StandingOvationRoundResolutionFactoryTest {

    @Test
    public void testCreateStandingOvationRoundResolutionFactory()
            throws Exception {
        RoundResolutionFactory factory = new StandingOvationRoundResolutionFactory();
    }

    @Test
    public void testBuildRoundCreator()
            throws Exception {
        RoundResolutionFactory factory = new StandingOvationRoundResolutionFactory();
        RoundCreator creator = factory.buildRoundCreator();
        assertThat(creator, is(notNullValue()));
        assertThat(creator, instanceOf(StandingOvationRoundCreator.class));
    }
}
