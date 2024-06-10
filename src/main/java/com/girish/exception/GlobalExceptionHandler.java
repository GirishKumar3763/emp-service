package com.girish.exception;

import java.net.URI;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler{

@ExceptionHandler(EmployeeNotFoundException.class)
 public ProblemDetail handleEmployeeNotFoundException(EmployeeNotFoundException ex)	{	 
	ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());	
	problemDetail.setType(URI.create("http://my-app-host.com/errors/not-found"));
	problemDetail.setTitle("Employee ID Not Found.");
	problemDetail.setProperty("timestamp", Instant.now());
	problemDetail.setProperty("errorCategory", "Generic");
	return problemDetail;	
	 
 }	

}
