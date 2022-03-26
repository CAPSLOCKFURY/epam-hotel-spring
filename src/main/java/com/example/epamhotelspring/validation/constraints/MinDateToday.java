package com.example.epamhotelspring.validation.constraints;

import com.example.epamhotelspring.validation.validators.MinDateTodayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinDateTodayValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinDateToday {
    String message() default "{errors.minDateToday}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
