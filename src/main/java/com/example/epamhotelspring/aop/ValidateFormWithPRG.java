package com.example.epamhotelspring.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * formName() - name of form
 * redirectUrlOnError() - url to which should be redirected if form has errorsl, e.g. redirect:/home
 * formAttributeIndex() - attribute index of form object
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ValidateFormWithPRG {
    String formName();
    String redirectUrlOnError();
    int formAttributeIndex() default 0;
}
