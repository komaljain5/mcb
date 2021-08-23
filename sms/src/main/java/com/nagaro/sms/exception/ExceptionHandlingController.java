package com.nagaro.sms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.common.collect.ImmutableMap;
import com.nagaro.sms.util.Constants;

/**
 * The Global Exception Handler for handling different exceptions.
 * @author komaljain01
 *
 */
@ControllerAdvice
public class ExceptionHandlingController {

	final static String MSG = "errorMessage";

	/**
	 * This is the handler method for ResourceNotFoundException
	 * 
	 * @author komaljain01
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		return new ResponseEntity<>(ImmutableMap.of(MSG, ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	/**
	 * This is the handler method for BadRequestException
	 * 
	 * @author komaljain01
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ BadRequestException.class, RequiredFieldsMissingException.class })
	public ResponseEntity<Object> requestRelatedExceptionHandler(Exception ex) {
		return new ResponseEntity<>(ImmutableMap.of(MSG, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * This is the handler method for AuthenticationFailedException
	 * 
	 * @author komaljain01
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AuthenticationFailedException.class)
	public ResponseEntity<Object> authenticationFailedExceptionHandler(AuthenticationFailedException ex) {
		return new ResponseEntity<>(ImmutableMap.of(MSG, ex.getMessage()), HttpStatus.UNAUTHORIZED);
	}

	/**
	 * This is the handler method for UnauthorizedException
	 * 
	 * @author komaljain01
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> unauthorizedExceptionHandler(UnauthorizedException ex) {
		return new ResponseEntity<>(ImmutableMap.of(MSG, ex.getMessage()), HttpStatus.FORBIDDEN);
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
		return new ResponseEntity<>(ImmutableMap.of(MSG, Constants.genericErrorMessage),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
