package com.example.epamhotelspring.utils;

import com.example.epamhotelspring.testutils.ParamMap;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThymeleafUtilsTest {

    private final ThymeleafUtils thymeleafUtils = new ThymeleafUtils();

    @ParameterizedTest
    @MethodSource("buildGetParametersCases")
    void buildGetParametersTest(Map<String, List<String>> params, String[] ignoreParams, String expected){
        String joinedParams = thymeleafUtils.buildGetParameters(params, ignoreParams);
        assertEquals(expected, joinedParams);
    }

    @ParameterizedTest
    @MethodSource("selectedIfCases")
    void selectedIfTest(Map<String, List<String>> params, String paramName, String value, boolean expected){
        boolean result = thymeleafUtils.selectedIf(params, paramName, value);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("sortFieldSelectedCases")
    void sortFieldSelectedTest(String param, String fieldName, boolean expected){
        boolean result = thymeleafUtils.sortFieldSelected(param, fieldName);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("sortDirectionSelectedCases")
    void sortDirectionSelectedTest(String param, String directionName, boolean expected){
        boolean result = thymeleafUtils.sortDirectionSelected(param, directionName);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> buildGetParametersCases(){
        ParamMap map1 = new ParamMap();
        map1.put("one", "1");

        ParamMap map2 = new ParamMap();
        map2.put("param", "1", "2");

        ParamMap map3 = new ParamMap();
        map3.put("myParam", "param");
        map3.put("mySecondParam", "param2");

        ParamMap map4 = new ParamMap();
        map4.put("param", "param");
        map4.put("ignoredParam", "ignoredParam");

        return Stream.of(
                Arguments.of(map1, new String[]{}, "one=1"),
                Arguments.of(map2, new String[]{}, "param=1&param=2"),
                Arguments.of(map3, new String[]{}, "myParam=param&mySecondParam=param2"),
                Arguments.of(map4, new String[]{"ignoredParam"}, "param=param"),
                Arguments.of(null, new String[]{}, "")
        );
    }

    private static Stream<Arguments> selectedIfCases(){
        ParamMap map1 = new ParamMap();
        map1.put("target", "value");
        map1.put("param", "param");

        ParamMap map2 = new ParamMap();
        map2.put("param", "param");

        ParamMap map3 = new ParamMap();
        map3.put("target", "value", "someParam");
        map3.put("parameter", "paramValue", "paramValue2");

        return Stream.of(
                Arguments.of(map1, "target", "value", true),
                Arguments.of(map2, "target", "value", false),
                Arguments.of(map3, "target", "value", true),
                Arguments.of(null, "", "", false)
        );
    }

    private static Stream<Arguments> sortFieldSelectedCases(){
        return Stream.of(
                Arguments.of("id,desc", "id", true),
                Arguments.of("name,asc", "name", true),
                Arguments.of("name", "name", false),
                Arguments.of(null, "", false),
                Arguments.of("", "", false),
                Arguments.of("field,desc", "otherField", false)
        );
    }

    private static Stream<Arguments> sortDirectionSelectedCases(){
        return Stream.of(
                Arguments.of("id,desc", "desc", true),
                Arguments.of("name,asc", "asc", true),
                Arguments.of("name", "desc", false),
                Arguments.of(null, "", false),
                Arguments.of("", "", false),
                Arguments.of("field,desc", "nonExistingDirection", false)
        );
    }

}
