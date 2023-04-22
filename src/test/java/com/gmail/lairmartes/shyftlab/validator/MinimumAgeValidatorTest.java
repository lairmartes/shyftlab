package com.gmail.lairmartes.shyftlab.validator;

import com.gmail.lairmartes.shyftlab.common.validator.MinimumAge;
import com.gmail.lairmartes.shyftlab.common.validator.MinimumAgeValidator;
import com.gmail.lairmartes.shyftlab.util.TestUtilLocalValidatorFactoryBean;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.annotation.Validated;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MinimumAgeValidatorTest {

    private static final int MINIMUM_AGE_TEST = 15;
    public static final String VALIDATION_TEST_ERROR_MESSAGE = "Minimum age for test must be " + MINIMUM_AGE_TEST;
    @Mock
    private Clock mockClock;

    private Validator validator;

    @Validated
    @Builder
    private static class POJOTest {

        @MinimumAge(value = MINIMUM_AGE_TEST, message = VALIDATION_TEST_ERROR_MESSAGE)
        private LocalDate birthDate;
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        setupClock();

        setupValidators();
    }

    private void setupClock() {
        var fixedTimeForTest = LocalDateTime.of(2023, 4, 21, 15, 30, 0);

        when(mockClock.getZone()).thenReturn(ZoneOffset.UTC);
        when(mockClock.instant()).thenReturn(fixedTimeForTest.toInstant(ZoneOffset.UTC));
    }

    private void setupValidators() {
        List<ConstraintValidator<?, ?>> constraintValidatorInstances = List.of(
                new MinimumAgeValidator(mockClock)
        );
        validator = new TestUtilLocalValidatorFactoryBean(constraintValidatorInstances);
    }

    @Test
    void isValid_whenAgeIsEqualThanMinimum_thenValidate() {

        LocalDate date15YearsFromNowMock = LocalDate.of(2008, 4, 20);
        final POJOTest objectToBeValidated = POJOTest.builder().birthDate(date15YearsFromNowMock).build();

        assertTrue(validator.validate(objectToBeValidated).isEmpty());
    }

    @Test
    void isValid_whenAgeLessThanMinimum_thenDoesNotValidate() {

        LocalDate dateAlmost15YearsFromNowMock = LocalDate.of(2008, 4, 22);
        final POJOTest objectToBeValidated = POJOTest.builder().birthDate(dateAlmost15YearsFromNowMock).build();

        Set<ConstraintViolation<POJOTest>> violations = validator.validate(objectToBeValidated);

        assertEquals(1, violations.size());
        assertTrue(violations.stream()
                .anyMatch(violation -> VALIDATION_TEST_ERROR_MESSAGE.equals(violation.getMessage())));
    }
}