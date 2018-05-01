package com.ippt.server.security.validator;

public class NumberRange {
    private int lowerBound;
    private int upperBound;

    public NumberRange(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public boolean contains(int value) {
        return value >= lowerBound && value <= upperBound;
    }
}
