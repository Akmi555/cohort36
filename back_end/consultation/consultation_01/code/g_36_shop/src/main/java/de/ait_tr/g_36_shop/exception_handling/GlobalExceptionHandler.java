package de.ait_tr.g_36_shop.exception_handling;

import de.ait_tr.g_36_shop.exception_handling.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 3-й способ обработки ошибок
// ПЛЮС - у нас есть глобальный обработчик ошибок, который
// позволяет перехватить исключения, возникающие в любом
// месте проекта, при этом отправив клиенту нужное сообщение
// вместе с нужным http-статусом
// ПЛЮС - всю логику обработки ошибок мы выносим в отдельное место
// проекта (то есть в этот класс), тем самым мы не загромождаем
// основную логику логикой обработки ошибок. Вся логика обработки
// ошибок у нас сконцентрирована в одном месте, что облегчает её доработку.
// МИНУС - при данном подходе мы не можем прописать какую-то специфичную
// логику обработки ошибок под какой-то конкретный контроллер.

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ThirdTestException.class)
    public ResponseEntity<Response> handlerException(ThirdTestException e){
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForthTestException.class)
    public ResponseEntity<Response> handlerException(ForthTestException e){
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // add classes with exceptions
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Response> handleException(ProductNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SavingProductException.class)
    public ResponseEntity<Response> handleException(SavingProductException e) {
        Throwable cause = e.getCause();
        Response response = cause == null ?
                new Response(e.getMessage()) :
                new Response(e.getMessage(), cause.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoActiveProductsException.class)
    public ResponseEntity<Response> handleException(NoActiveProductsException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
