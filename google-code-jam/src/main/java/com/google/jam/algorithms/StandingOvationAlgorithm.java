package com.google.jam.algorithms;

import java.util.function.Function;

import static java.lang.Math.max;

public final class StandingOvationAlgorithm
        implements Function<String, String> {

    @Override
    public String apply(String task) {
        final String audience = task.split("\\s+")[1];
        int shinedAudience = 0;
        int invitedFriends = 0;
        for (int currentShineLevel = 0; currentShineLevel < audience.length(); currentShineLevel++) {
            invitedFriends = max(currentShineLevel - shinedAudience, invitedFriends);
            shinedAudience += charToInt(audience.charAt(currentShineLevel));
        }
        return Integer.toString(invitedFriends);
    }

    private int charToInt(final char c) {
        return c - '0';
    }
}
