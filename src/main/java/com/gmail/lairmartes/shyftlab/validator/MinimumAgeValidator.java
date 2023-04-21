package com.gmail.lairmartes.shyftlab.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class MinimumAgeValidator implements ConstraintValidator<MinimumAge, LocalDate> {

    private final Clock clock;
    private int minimumAge;

    @Override
    public void initialize(MinimumAge constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.minimumAge = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        final LocalDate now = LocalDate.now(clock);
        return ChronoUnit.YEARS.between(now, localDate) >= this.minimumAge;
    }
}
