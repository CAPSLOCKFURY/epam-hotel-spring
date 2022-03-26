package com.example.epamhotelspring.validation.constraints;

import com.example.epamhotelspring.validation.validators.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "{errors.emailAlreadyExists}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
