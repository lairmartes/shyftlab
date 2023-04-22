package com.gmail.lairmartes.shyftlab.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValueOfScoreValidator implements ConstraintValidator<ValueOfScore, String>{
    @Override
    public void initialize(ValueOfScore constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String score, ConstraintValidatorContext constraintValidatorContext) {
        if (score == null) return false;
        return Arrays.stream(com.gmail.lairmartes.shyftlab.result.enums.Score.values()).map(Enum::name).toList()
                .contains(score);
    }
}
