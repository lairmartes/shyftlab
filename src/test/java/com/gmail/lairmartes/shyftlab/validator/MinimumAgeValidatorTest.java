package com.gmail.lairmartes.shyftlab.validator;

import com.gmail.lairmartes.shyftlab.util.TestUtilLocalValidatorFactoryBean;
import jakarta.validation.*;
import lombok.Builder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.annotation.Validated;

import java.time.*;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinimumAgeValidatorTest {

    private static final int MINIMUM_AGE_TEST = 15;
    @Mock
    private Clock mockClock;

    private List<ConstraintValidator<?,?>> constraintValidatorInstances;

    private Validator validator;

    @Validated
    @Builder
    private static class POJOTest {

        @MinimumAge(value = MINIMUM_AGE_TEST)
        private LocalDate birthDate;
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        setupClock();

        setupValidators();
    }

    private void setupClock() {
        var fixedTimeForTest =LocalDateTime.of(2023, 4, 21, 15, 30, 0);

        when(mockClock.getZone()).thenReturn(ZoneOffset.UTC);
        when(mockClock.instant()).thenReturn(fixedTimeForTest.toInstant(ZoneOffset.UTC));
    }

    private void setupValidators() {
        constraintValidatorInstances = List.of(
                new MinimumAgeValidator(mockClock)
        );
        validator = new TestUtilLocalValidatorFactoryBean(constraintValidatorInstances);
    }

    @Test
    void isValid_whenAgeIsGreaterThanMinimum_thenReturnsTrue() {

        final POJOTest objectToBeValidate = POJOTest.builder().birthDate(LocalDate.of(2008, 4, 20)).build();

        Set<ConstraintViolation<POJOTest>> violations = validator.validate(objectToBeValidate);

        assertTrue(violations.isEmpty());
    }
}