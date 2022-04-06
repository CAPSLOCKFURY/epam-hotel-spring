package com.example.epamhotelspring.utils;

public class StringUtils {

    private static final String REPLACE_HTML_REGEX = "<[^>]*>";

    public static String removeRegexFromString(String s) {
        return s.replaceAll(REPLACE_HTML_REGEX, "");
    }

    private StringUtils(){
        throw new UnsupportedOperationException();
    }

}
