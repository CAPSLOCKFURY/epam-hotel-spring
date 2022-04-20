package com.example.epamhotelspring.service.utils;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedList;
import java.util.List;

public class ServiceErrors {

    private final List<String> errors = new LinkedList<>();

    public void reject(String error){
        errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public BindingResult toBindingResult(BindingResult bindingResult){
        for(String error : errors){
            bindingResult.reject(error);
        }
        return bindingResult;
    }

    public RedirectAttributes toRedirectAttributes(RedirectAttributes redirectAttributes){
        return toRedirectAttributes(redirectAttributes, "errors");
    }

    public RedirectAttributes toRedirectAttributes(RedirectAttributes redirectAttributes, String errorNamespace){
        redirectAttributes.addFlashAttribute(errorNamespace, errors);
        return redirectAttributes;
    }
}
