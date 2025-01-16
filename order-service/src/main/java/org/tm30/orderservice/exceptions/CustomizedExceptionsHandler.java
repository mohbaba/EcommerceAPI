package org.tm30.orderservice.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
public class CustomizedExceptionsHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EcommerceException.class)
    public final ResponseEntity<Object> handleGlobalException(EcommerceException ecommerceException, final WebRequest request) {
        HttpStatus status = determineHttpStatus(ecommerceException);
        final ExceptionResponse exceptionResponse = new ExceptionResponse().builder()
                .error(status.getReasonPhrase())
                .path(request.getDescription(false))
                .message(ecommerceException.getMessage())
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionResponse, status);
    }

    private HttpStatus determineHttpStatus(EcommerceException exception) {
        if (exception instanceof OrderNotFoundException) {
            return HttpStatus.NOT_FOUND;
        }

        return HttpStatus.BAD_REQUEST;
    }

}

