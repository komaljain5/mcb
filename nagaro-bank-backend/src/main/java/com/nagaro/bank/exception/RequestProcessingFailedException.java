package com.nagaro.bank.exception;

public class RequestProcessingFailedException extends RuntimeException {

	private static final long serialVersionUID = 3926932048342942000L;

	public RequestProcessingFailedException(String message) {
		super(message);
	}

}
