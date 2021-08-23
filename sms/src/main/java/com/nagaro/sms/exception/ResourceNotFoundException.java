package com.nagaro.sms.exception;

/**
 * Exception class to be used if no resource could be found for the user's input request
 * @author komaljain01
 *
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -363410565397017442L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
