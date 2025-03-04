package com.kimandkang.rouleatt.utils;

import java.util.Arrays;
import java.util.List;

public class RestaurantUtils {

    public static List<String> split(String exclude) {
        if (exclude != null && !exclude.isEmpty()) {
            return Arrays.stream(exclude.split(",")).toList();
        }
        return null;
    }
}
