package de.ait_tr.lesson_15_validation_error_handling.exception_handling.exceptions;

public class NoActiveProductsException extends RuntimeException {

    public NoActiveProductsException(String message) {
        super(message);
    }
}