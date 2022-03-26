package com.example.epamhotelspring.validation.validators;

import com.example.epamhotelspring.validation.constraints.MinDateToday;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MinDateTodayValidator implements ConstraintValidator<MinDateToday, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null){
            return true;
        }
        LocalDate today = LocalDate.now();
        return !date.isBefore(today);
    }
}
