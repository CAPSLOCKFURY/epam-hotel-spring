package com.example.epamhotelspring.validation.constraints;

import com.example.epamhotelspring.validation.validators.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "{errors.userNameAlreadyExists}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
