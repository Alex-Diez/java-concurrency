package com.google.jam.unit.infinitehouseofpancakes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
        {
                InputInfiniteHouseOfPancakesRoundTest.class,
                InfiniteHouseOfPancakesRoundTaskFormatValidationTest.class
        }
)
public class InfiniteHouseOfPancakesRoundCreatorTest {
}
