package com.example.epamhotelspring.validation.validators;

import com.example.epamhotelspring.validation.constraints.OneDateAfterAnother;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class OneDateAfterAnotherValidator implements ConstraintValidator<OneDateAfterAnother, Object> {

    private String beforeDateField;

    private String afterDateField;

    @Override
    public void initialize(OneDateAfterAnother constraintAnnotation) {
        beforeDateField = constraintAnnotation.beforeDateField();
        afterDateField = constraintAnnotation.afterDateField();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        BeanWrapperImpl bean = new BeanWrapperImpl(o);
        LocalDate beforeDate = (LocalDate) bean.getPropertyValue(beforeDateField);
        LocalDate afterDate = (LocalDate) bean.getPropertyValue(afterDateField);
        if(afterDate == null || beforeDate == null){
            return true;
        }
        return beforeDate.isBefore(afterDate);
    }
}
