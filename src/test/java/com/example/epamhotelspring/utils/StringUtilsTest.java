package com.example.epamhotelspring.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTest {

    @ParameterizedTest
    @MethodSource("removeRegexFromStringCases")
    void removeRegexFromStringTest(String input, String expected){
        String cleanedString = StringUtils.removeRegexFromString(input);
        assertEquals(expected, cleanedString);
    }

    private static Stream<Arguments> removeRegexFromStringCases(){
        return Stream.of(
                Arguments.of("String", "String"),
                Arguments.of("<h1>String</h1>", "String"),
                Arguments.of("</a>String", "String"),
                Arguments.of("<form></form>String<h1 attr=\"someValue\">", "String")
        );
    }

}
