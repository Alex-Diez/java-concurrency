package com.google.jam.standingovation;

import java.util.Map;

import com.google.jam.Round;

public interface StandingOvationResolver
		extends AutoCloseable {

	Map<Integer, Integer> solve(final Round round);
}
