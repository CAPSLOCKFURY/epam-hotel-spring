package com.example.epamhotelspring.utils;

import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service("thymeleafUtils")
public class ThymeleafUtils {

    /**
     * Joins given map of get params with ampersand
     * @param params Map of get parameters
     * @param ignoreParams Parameter names that should be ignored from joining
     * @return Joined string of get params, delimiter is ampersand
     */
    public String buildGetParameters(Map<String, List<String>> params, String ...ignoreParams){
        if(params == null || params.isEmpty()) {
            return "";
        }
        params.keySet().removeAll(Set.of(ignoreParams));
        return params.entrySet().stream()
                .flatMap(e -> e.getValue().stream().map(value -> new AbstractMap.SimpleEntry<>(e.getKey(), value)))
                .map(e -> e.getKey().concat("="+e.getValue()))
                .collect(Collectors.joining("&"));
    }


    /**
     * @param params Map of parameters
     * @param paramName Name of map key
     * @param value value of map key to compare
     * @return true if map entry with given key paramName equals to value
     */
    public boolean selectedIf(Map<String, List<String>> params, String paramName, String value){
        if(params == null || value.isEmpty()){
            return false;
        }
        List<String> paramList = params.get(paramName);
        if(paramList == null){
            return false;
        }
        return paramList.contains(value);
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
