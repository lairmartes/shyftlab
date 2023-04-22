package com.gmail.lairmartes.shyftlab.util;

import jakarta.validation.Configuration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorFactory;
import lombok.NonNull;
import org.hibernate.validator.HibernateValidator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;
import java.util.Optional;

public class TestUtilLocalValidatorFactoryBean extends LocalValidatorFactoryBean {

    private final List<ConstraintValidator<?, ?>> validationInstancesForTesting;


    public TestUtilLocalValidatorFactoryBean(List<ConstraintValidator<?, ?>> validatorInstancesForTesting) {
        this.validationInstancesForTesting = validatorInstancesForTesting;
        setProviderClass(HibernateValidator.class);
        afterPropertiesSet();
    }

    @Override
    protected void postProcessConfiguration(@NonNull Configuration<?> configuration) {
        super.postProcessConfiguration(configuration);

        ConstraintValidatorFactory constraintValidatorFactory =
                configuration.getDefaultConstraintValidatorFactory();

        configuration.constraintValidatorFactory(
                new ConstraintValidatorFactory() {
                    @Override
                    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> keyClass) {

                        return validationInstancesForTesting
                                .stream()
                                .filter(constraintValidator -> keyClass.equals(constraintValidator.getClass()))
                                .findAny()
                                .map(constraintValidator -> (T) constraintValidator)
                                .orElseGet(() -> constraintValidatorFactory.getInstance(keyClass));
                    }

                    @Override
                    public void releaseInstance(ConstraintValidator<?, ?> constraintValidator) {
                        constraintValidatorFactory.releaseInstance(constraintValidator);
                    }
                }
        );
    }
}
