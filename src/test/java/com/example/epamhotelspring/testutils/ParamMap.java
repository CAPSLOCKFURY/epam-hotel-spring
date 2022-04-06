package com.example.epamhotelspring.testutils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ParamMap extends HashMap<String, List<String>> {

    public void put(String key, String ...values){
        List<String> list = new LinkedList<>(Arrays.asList(values));
        put(key, list);
    }

}
