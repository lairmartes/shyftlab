package com.gmail.lairmartes.shyftlab.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;

public class TestUtilConstraintViolationBuilder {

    public static ConstraintViolation<String> buildConstraintViolation(final String message) {
        return new ConstraintViolation<>() {
            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public String getMessageTemplate() {
                return null;
            }

            @Override
            public String getRootBean() {
                return null;
            }

            @Override
            public Class<String> getRootBeanClass() {
                return null;
            }

            @Override
            public Object getLeafBean() {
                return "getLeafBean";
            }

            @Override
            public Object[] getExecutableParameters() {
                return null;
            }

            @Override
            public Object getExecutableReturnValue() {
                return null;
            }

            @Override
            public Path getPropertyPath() {
                return null;
            }

            @Override
            public Object getInvalidValue() {
                return null;
            }

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor() {
                return null;
            }

            @Override
            public <U> U unwrap(Class<U> aClass) {
                return null;
            }
        };
    }
}
