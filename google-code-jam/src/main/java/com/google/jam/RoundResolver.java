package com.google.jam;

import java.util.Map;
import java.util.function.Function;

public interface RoundResolver {

	Map<Integer, Integer> solve(final Round round, final Function<String, Integer> algorithm);
}
