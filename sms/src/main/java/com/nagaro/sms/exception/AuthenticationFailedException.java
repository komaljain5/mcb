package com.nagaro.sms.exception;

/**
 * Exception class to be used if user authentication fails due to invalid credentials
 * @author komaljain01
 *
 */
public class AuthenticationFailedException extends RuntimeException {

	private static final long serialVersionUID = 7122936118103531899L;

	public AuthenticationFailedException(String message) {
		super(message);
	}

}
