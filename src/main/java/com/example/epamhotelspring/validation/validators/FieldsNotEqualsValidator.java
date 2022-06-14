package com.example.epamhotelspring.validation.validators;

import com.example.epamhotelspring.validation.constraints.FieldsNotEquals;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsNotEqualsValidator implements ConstraintValidator<FieldsNotEquals, Object> {

    private String firstField;

    private String secondField;

    @Override
    public void initialize(FieldsNotEquals constraintAnnotation) {
        firstField = constraintAnnotation.firstField();
        secondField = constraintAnnotation.secondField();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        BeanWrapperImpl bean = new BeanWrapperImpl(o);
        Object first = bean.getPropertyValue(firstField);
        Object second = bean.getPropertyValue(secondField);
        if(first == null || second == null){
            return false;
        }
        return !first.equals(second);
    }
}
