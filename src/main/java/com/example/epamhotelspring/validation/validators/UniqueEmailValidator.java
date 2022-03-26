package com.example.epamhotelspring.validation.validators;

import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.service.UserService;
import com.example.epamhotelspring.validation.constraints.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        User user = userService.getUserByEmail(email);
        return user == null;
    }
}
