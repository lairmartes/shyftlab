package com.gmail.lairmartes.shyftlab.common.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = MinimumAgeValidator.class)
@Documented
public @interface ValueOfScore {
    String message() default "Provide a score A, B, C, D, E or F.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
