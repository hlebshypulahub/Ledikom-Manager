package org.openjfx.utilities.comparators;

import java.util.Comparator;

public class IntComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer int1, Integer int2) {
        return int1.compareTo(int2);
    }
}