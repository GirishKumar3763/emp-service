package com.girish.exception;

public class EmployeeAlreadyFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmployeeAlreadyFoundException(String message) {
		super(message);
	}

}
