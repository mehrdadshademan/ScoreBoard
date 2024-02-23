package com.sportradar.scoreboard.wer.rest.exception;

import com.sportradar.scoreboard.exception.MatchBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(MatchBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public void handlerMobileBadRequest(MatchBadRequestException e){
        new ResponseEntity<>( e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
