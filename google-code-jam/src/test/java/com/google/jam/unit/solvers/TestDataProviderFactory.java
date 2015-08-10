package com.google.jam.unit.solvers;

class TestDataProviderFactory {

    public TestDataProvider createDataProvider(final char roundLetter) {
        switch (roundLetter) {
            case 'A':
                return new StandingOvationTestDataProvider();
            case 'B':
                return new InfiniteHouseOfPancakesTestDataProvider();
        }
        throw new RuntimeException("impossible situation!!!");
    }
}
