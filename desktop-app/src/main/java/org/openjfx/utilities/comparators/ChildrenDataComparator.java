package org.openjfx.utilities.comparators;

import java.util.Comparator;

public class ChildrenDataComparator implements Comparator<String> {
    @Override
    public int compare(String data1, String data2) {
        return data1 == null ? -1 : data2 ==  null ? 1 : Integer.valueOf(data1.substring(0, 1)).compareTo(Integer.valueOf(data2.substring(0, 1)));
    }
}
