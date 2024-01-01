package com.karson.ecommerce.common.utils;

public class StringUtil {

    private StringUtil() {}

    public static boolean isBlank(String s) {
        return null == s || s.isBlank();
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }
}
