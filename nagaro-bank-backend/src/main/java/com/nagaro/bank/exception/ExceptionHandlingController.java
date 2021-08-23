package com.nagaro.bank.exception;

import java.util.HashMap;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



/**
 * The Global Exception Handler for handling different exceptions.
 * @author komaljain01
 *
 */
@Primary
@ControllerAdvice
public class ExceptionHandlingController {

	final static String MSG = "message";

	/**
	 * This is the handler method for AuthenticationFailedException
	 * 
	 * @author komaljain01
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Object> authenticationFailedExceptionHandler(AuthenticationException ex) {
		HashMap<String, String> map = new HashMap();
		map.put(MSG, ex.getMessage());
		return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
	}
	/**
	 * This is the generic handler method for handling all remaining exceptions
	 * 
	 * @author komaljain01
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> globalExceptionHandler(Exception ex) {
		HashMap<String, String> map = new HashMap();
		map.put(MSG, ex.getMessage());
		return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
