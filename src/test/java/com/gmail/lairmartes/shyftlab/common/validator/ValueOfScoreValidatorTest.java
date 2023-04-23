package com.gmail.lairmartes.shyftlab.common.validator;

import com.gmail.lairmartes.shyftlab.util.TestUtilLocalValidatorFactoryBean;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.annotation.Validated;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ValueOfScoreValidatorTest {

    @Mock
    private Clock mockClock;

    private Validator validator;

    @Validated
    @Builder
    private static class POJOTest {

        @ValueOfScore
        private String score;
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

    @Nested
    class IsValid {
        @Test
        void whenValueIsInEnum_thenValidate() {

            final POJOTest objectToBeValidated = POJOTest.builder().score("A").build();

            assertTrue(validator.validate(objectToBeValidated).isEmpty());
        }

        @Test
        void whenScoreProvidedIsNull_thenDoesNotValidate() {

            final POJOTest objectToBeValidated = POJOTest.builder().score(null).build();

            final Set<ConstraintViolation<POJOTest>> violations = validator.validate(objectToBeValidated);

            assertEquals(1, violations.size());
            assertTrue(violations.stream()
                    .anyMatch(violation -> "Provide a score A, B, C, D, E or F.".equals(violation.getMessage())));
        }

        @Test
        void whenScoreIsNotInEnum_thenDoesNotValidate() {

            final POJOTest objectToBeValidated = POJOTest.builder().score("ZZ").build();

            final Set<ConstraintViolation<POJOTest>> violations = validator.validate(objectToBeValidated);

            assertEquals(1, violations.size());
            assertTrue(violations.stream()
                    .anyMatch(violation -> "Provide a score A, B, C, D, E or F.".equals(violation.getMessage())));
        }
    }
}