package com.example.epamhotelspring.validation.constraints;


import com.example.epamhotelspring.validation.validators.OneDateAfterAnotherValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OneDateAfterAnotherValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface OneDateAfterAnother {
    String beforeDateField();
    String afterDateField();
    String message() default "{errors.oneDayIsBeforeAnother}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
