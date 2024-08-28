package de.ait_tr.lesson_15_validation_error_handling.exception_handling;

import de.ait_tr.lesson_15_validation_error_handling.exception_handling.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonAdvice {

    @ExceptionHandler(ThirdTestException.class)
    public ResponseEntity<Response> handleException(ThirdTestException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FourthTestException.class)
    public ResponseEntity<Response> handleException(FourthTestException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // когда запрашиваем продукт, которого нет в базе
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Response> handleException(ProductNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // когда сохранем продукт, но произошла ошибка на сервере или в БД
    @ExceptionHandler(SavingProductException.class)
    public ResponseEntity<Response> handleException(SavingProductException e) {
        Throwable cause = e.getCause();
        Response response = cause == null ?
                new Response(e.getMessage()) :
                new Response(e.getMessage(), cause.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // когда просим продукт, но он не активен
    @ExceptionHandler(NoActiveProductsException.class)
    public ResponseEntity<Response> handleException(NoActiveProductsException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}