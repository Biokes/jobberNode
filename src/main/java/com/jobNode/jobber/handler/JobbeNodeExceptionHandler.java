package com.jobNode.jobber.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static com.jobNode.jobber.exception.ExceptionMessages.INVALID_DETAILS;
import static com.jobNode.jobber.exception.ExceptionMessages.SOMETHING_WENT_WRONG;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class JobbeNodeExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<?> dataIntegrityConstraint(DataIntegrityViolationException exception){
        return ResponseEntity.status(BAD_REQUEST).body(Map.
                of("error",INVALID_DETAILS.getMessage(),"success",false));
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> dataIntegrityConstraint(Exception exception){
        return ResponseEntity.status(BAD_REQUEST).body(Map.
                of("error",SOMETHING_WENT_WRONG.getMessage(),"success",false));
    }
}
