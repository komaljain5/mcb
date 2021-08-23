package com.nagaro.sms.exception;

/**
 * Exception class to be used if user doesn't have necessary permission to perform an action
 * @author komaljain01
 *
 */
public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = -6085870261945894860L;

	public UnauthorizedException(String message) {
		super(message);
	}

}
