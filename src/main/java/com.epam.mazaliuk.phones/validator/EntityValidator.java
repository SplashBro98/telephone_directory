package com.epam.mazaliuk.phones.validator;

@FunctionalInterface
public interface EntityValidator<T> {

    void validate(T t);
}
