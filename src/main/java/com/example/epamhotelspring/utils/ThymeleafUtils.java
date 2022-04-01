package com.example.epamhotelspring.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service("thymeleafUtils")
public class ThymeleafUtils {

    public String buildGetParameters(Map<String, String> params, String ...ignoreParams){
        params.keySet().removeAll(Set.of(ignoreParams));
        return params.entrySet().stream()
                .map(e -> e.getKey().concat("="+e.getValue()))
                .collect(Collectors.joining("&"));
    }

}
