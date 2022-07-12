package ru.itmo.nfomkin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itmo.nfomkin.domain.Response;

@ControllerAdvice
public class ErrorController {

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Response> ValidationException(RuntimeException exception) {
    String errorMessage = (exception != null ? exception.getMessage() : "Unknown error");
    return new ResponseEntity<Response>(new Response(errorMessage), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Response> exception(Exception exception){
    String errorMessage = (exception != null ? exception.getMessage() : "Unknown error");
    return new ResponseEntity<Response>(new Response(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
