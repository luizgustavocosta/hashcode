package com.roche.hashcode.library;

import java.util.ArrayList;
import java.util.List;

public class LibraryUtil {

    public static List<Integer> listOf(String line) {
        List<Integer> elements = new ArrayList<>();
        for (String column : line.split(" ")) {
            elements.add(Integer.parseInt(column));
        }
        return elements;
    }

    public static Integer[] arrayOf(String line) {
        final String[] columns = line.split(" ");
        Integer[] elements = new Integer[columns.length];
        for (int index = 0; index < columns.length; index++) {
            elements[index] = Integer.parseInt(columns[index]);
        }
        return elements;
    }
}
