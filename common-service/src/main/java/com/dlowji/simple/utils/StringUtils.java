package com.dlowji.simple.utils;

public class StringUtils {
    public static boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }
}
