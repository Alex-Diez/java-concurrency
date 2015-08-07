package com.google.jam.unit.solvers;

import java.util.Map;

public interface TestDataProvider {

    Map<Integer, Integer> provideSmallSetOfTestData();

    Map<Integer, Integer> provideSmallSetOfPracticeData();

    Map<Integer, Integer> provideLargeSetOfPracticeData();
}
