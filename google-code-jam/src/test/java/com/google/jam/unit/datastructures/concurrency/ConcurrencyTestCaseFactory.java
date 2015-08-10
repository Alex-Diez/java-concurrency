package com.google.jam.unit.datastructures.concurrency;

import java.util.Arrays;
import java.util.Collection;

class ConcurrencyTestCaseFactory {

    public static final Object[][] DATA = new Object[][] {
            {3, 2},
            {4, 2},
            {6, 4},
            {8, 4},
            {12, 8},
            {16, 8},
            {24, 16},
            {32, 16},
            {48, 32},
            {64, 32}
    };

    public Collection<Object[]> createNumberOfThreadForTestCases(final int repeatTimes) {
        final Object[][] data = new Object[DATA.length * repeatTimes][2];
        for (int j = 0; j < DATA.length; j++) {
            for (int i = 0; i < repeatTimes; i++) {
                System.arraycopy(DATA[j], 0, data[j * DATA.length + i], 0, DATA[j].length);
            }
        }
        return Arrays.asList(data);
    }
}
