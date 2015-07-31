package com.google.jam.unit.standingovation.singlethread;

import java.util.function.Function;

import com.google.jam.RoundCreator;
import com.google.jam.RoundResolver;
import com.google.jam.standingovation.singlethread.SingleThreadStandingOvationRoundResolver;
import com.google.jam.standingovation.StandingOvationRoundCreator;
import com.google.jam.unit.standingovation.AbstractStandingOvationRoundResolverTest;

import org.junit.Before;

public class SingleThreadStandingOvationResolverTest
		extends AbstractStandingOvationRoundResolverTest {

	private RoundCreator creator;
	private RoundResolver resolver;

	public SingleThreadStandingOvationResolverTest(Function<String, Integer> algorithm) {
		super(algorithm);
	}

	@Before
	public void setUp()
			throws Exception {
		resolver = new SingleThreadStandingOvationRoundResolver();
		creator = new StandingOvationRoundCreator(false);
	}

	@Override
	public RoundCreator getCreator() {
		return creator;
	}

	@Override
	public RoundResolver getResolver() {
		return resolver;
	}
}
