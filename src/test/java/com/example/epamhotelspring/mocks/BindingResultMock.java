package com.example.epamhotelspring.mocks;

import org.mockito.Mockito;
import org.springframework.validation.BindingResult;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class BindingResultMock {

    public static BindingResult mockBindingResult(){
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        List<String> errors = new LinkedList<>();
        Mockito.doAnswer(invocationOnMock -> {
            errors.add((String) invocationOnMock.getArguments()[0]);
            return invocationOnMock;
        }).when(bindingResult).reject(any(String.class));
        Mockito.when(bindingResult.hasErrors()).thenReturn(!errors.isEmpty());
        return bindingResult;
    }

}
