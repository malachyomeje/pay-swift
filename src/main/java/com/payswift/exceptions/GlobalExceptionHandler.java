package com.payswift.exceptions;

import com.payswift.dtos.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildErrorResponse(Object error, HttpStatus status){
        return ErrorResponse.builder()
                .httpStatusCode(status.value())
                .message(Collections.singletonList(error))
                .responseDate(LocalDateTime.now(Clock.systemUTC()))
                .build();
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse UserNotFoundException(UserNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {WalletTransactionException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse transactionNotFoundException(WalletTransactionException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }


}
