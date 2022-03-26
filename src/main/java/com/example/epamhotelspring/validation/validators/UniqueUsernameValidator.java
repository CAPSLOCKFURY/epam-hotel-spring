package com.example.epamhotelspring.validation.validators;


import com.example.epamhotelspring.service.UserService;
import com.example.epamhotelspring.validation.constraints.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        try {
            userService.loadUserByUsername(username);
            return false;
        } catch (UsernameNotFoundException e){
            return true ;
        }
    }
}
