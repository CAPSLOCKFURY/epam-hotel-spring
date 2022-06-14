package com.example.epamhotelspring.validation.constraints;

import com.example.epamhotelspring.validation.validators.FieldsNotEqualsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FieldsNotEqualsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsNotEquals {
    String firstField();
    String secondField();
    String message() default "{errors.fieldsNotEquals}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
