package com.example.epamhotelspring.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service("thymeleafUtils")
public class ThymeleafUtils {

    /**
     * Joins given map of get params with ampresand
     * @param params Map of get parameters
     * @param ignoreParams Parameter names that should be ignored from joining
     * @return Joined string of get params, delimiter is ampersand
     */
    public String buildGetParameters(Map<String, String> params, String ...ignoreParams){
        if(params == null || params.isEmpty()) {
            return "";
        }
        params.keySet().removeAll(Set.of(ignoreParams));
        return params.entrySet().stream()
                .map(e -> e.getKey().concat("="+e.getValue()))
                .collect(Collectors.joining("&"));
    }

    /**
     * Returns true whether field is in sort expression like 'id,asc'
     * @param param Get parameter containing sort expression
     * @param fieldName name of sort field
     * @return true if field is in param
     */
    public boolean sortFieldSelected(String param, String fieldName){
        if(param == null || param.isEmpty() || param.isBlank()){
            return false;
        }
        String[] string = param.split(",");
        if(string.length != 2){
            return false;
        }
        return string[0].equals(fieldName);
    }

    /**
     * Returns true whether direction is in sort expression like 'id,asc'
     * @param param Get parameter containing sort expression
     * @param directionName name of direction field
     * @return true if direction name is in param
     */
    public boolean sortDirectionSelected(String param, String directionName){
        if(param == null || param.isEmpty() || param.isBlank()){
            return false;
        }
        String[] strings = param.split(",");
        if(strings.length != 2){
            return false;
        }
        return strings[1].equals(directionName);
    }

}
