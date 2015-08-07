package com.google.jam.solvers;

import java.util.Map;
import java.util.function.Function;

import com.google.jam.Round;

public interface RoundResolver {

    Map<Integer, Integer> solve(final Round round, final Function<String, Integer> algorithm);
}
