package com.nagaro.sms.exception;

/**
 * Exception class to be used if user request is not as per requisites
 * @author komaljain01
 *
 */
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 3926932048342942000L;

	public BadRequestException(String message) {
		super(message);
	}

}
