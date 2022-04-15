package com.example.epamhotelspring.mocks;

import org.mockito.Mockito;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

public class RedirectAttributesMock {

    public static RedirectAttributes redirectAttributesMock(){
        RedirectAttributes bindingResult = Mockito.mock(RedirectAttributes.class);
        Map<String , Object> errors = new HashMap<>();
        Mockito.doAnswer(invocationOnMock -> {
            errors.put((String) invocationOnMock.getArguments()[0], invocationOnMock.getArguments()[1]);
            return invocationOnMock;
        }).when(bindingResult).addFlashAttribute(any(String.class), any(Object.class));
        return bindingResult;
    }

}
