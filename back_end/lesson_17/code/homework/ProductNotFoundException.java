package de.ait_tr.lesson_15_validation_error_handling.exception_handling.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long productId) {
        super(String.format("Product with id %d not found", productId));
    }
}