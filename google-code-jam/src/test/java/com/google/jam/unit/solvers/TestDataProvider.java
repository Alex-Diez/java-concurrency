package com.google.jam.unit.solvers;

import java.util.Map;

interface TestDataProvider {

    Map<Integer, Integer> provideSmallSetOfTestData();

    Map<Integer, Integer> provideSmallSetOfPracticeData();

    Map<Integer, Integer> provideLargeSetOfPracticeData();
}
