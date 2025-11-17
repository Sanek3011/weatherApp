package com.hh.util;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class WindUtils {

    private static final Map<Integer, String> sectorMap;

    static {
        sectorMap = Map.of(0, "N",
                1, "NE",
                2 ,"E",
                3, "SE",
                4, "S",
                5, "SW",
                6, "W",
                7, "NW");
    }

    public static String getWindDirection(Integer deg) {
        if (deg == null) {
            return "unknown";
        }
        int d = ((deg % 360) + 360) % 360;
        int key = d / 45;

        return sectorMap.getOrDefault(key, "unknown");
    }
}
