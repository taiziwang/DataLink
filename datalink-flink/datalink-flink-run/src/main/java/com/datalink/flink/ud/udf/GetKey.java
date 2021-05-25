package com.datalink.flink.ud.udf;

import org.apache.flink.table.functions.ScalarFunction;

public class GetKey extends ScalarFunction {

    public String eval(String map, String key, String defaultValue) {
        if (map == null || !map.contains(key)) {
            return defaultValue;
        }
        String[] maps = map.replaceAll("\\{", "").replaceAll("\\}", "").split(",");
        for (int i = 0; i < maps.length; i++) {
            String[] items = maps[i].split("=");
            if (items.length >= 2) {
                if (key.equals(items[0].trim())) {
                    return items[1];
                }
            }
        }
        return defaultValue;
    }
}