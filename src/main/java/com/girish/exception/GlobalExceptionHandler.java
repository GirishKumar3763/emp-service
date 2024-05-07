package com.girish.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({EmployeeNotFoundException.class})
    public ResponseEntity<ErrorMessage> EmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest request) {		
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.NOT_FOUND.value(),
		        new Date(),
		        ex.getMessage(),
		        request.getDescription(false));
		
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);

    }
   
	@ExceptionHandler({EmployeeAlreadyFoundException.class})
    public ResponseEntity<ErrorMessage> handleEmployeeAlreadyExistsException(EmployeeAlreadyFoundException ex,WebRequest request) {
		
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.ALREADY_REPORTED.value(),
		        new Date(),
		        ex.getMessage(),
		        request.getDescription(false));
		 return new ResponseEntity<ErrorMessage>(message, HttpStatus.ALREADY_REPORTED);
    }
	
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessage> handleException(Exception ex,WebRequest request) {
    	ErrorMessage message = new ErrorMessage(
    	        HttpStatus.INTERNAL_SERVER_ERROR.value(),
    	        new Date(),
    	        ex.getMessage(),
    	        request.getDescription(false));
    	return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
