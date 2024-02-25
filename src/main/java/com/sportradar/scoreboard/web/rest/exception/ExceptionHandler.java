package com.sportradar.scoreboard.web.rest.exception;

import com.sportradar.scoreboard.exceptions.MatchBadRequestInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * this class is ExceptionHandler controller for web view and handle the exceptions
 */
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(MatchBadRequestInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public void handlerMobileBadRequest(MatchBadRequestInputException e){
        new ResponseEntity<>( e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
