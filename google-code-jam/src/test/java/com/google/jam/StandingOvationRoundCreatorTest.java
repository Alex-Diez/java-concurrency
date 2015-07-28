package com.google.jam;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
		{
				InputStandingOvationRoundTest.class,
				StandingOvationRoundTaskFormatValidation.class
		}
)
public class StandingOvationRoundCreatorTest {
}
