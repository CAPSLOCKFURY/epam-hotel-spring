package com.example.epamhotelspring.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * formName() - name of form
 * redirectUrlOnError() - url to which should be redirected if form has errors, e.g. 'redirect:/home'
 *<p>
 *     Note: redirectUrlOnError String is parsed as SpEL expression, so if you want plain string make sure it is surrounded
 *     with single quotes.
 *</p>
 * <p>
 *     Note: arguments of method on which this annotation on, is loaded into SpEL EvalContext
 * </p>
 * formAttributeIndex() - attribute index of form object
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ValidateFormWithPRG {
    String formName();
    String redirectUrlOnError();
    int formAttributeIndex() default 0;
}
