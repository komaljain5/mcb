package com.nagaro.sms.exception;

/**
 * Exception class to be used if request is missing one or more required fields
 * @author komaljain01
 *
 */
public class RequiredFieldsMissingException extends RuntimeException {

	private static final long serialVersionUID = -2173082547462918404L;

	public RequiredFieldsMissingException(String message) {
		super(message);
	}

}
