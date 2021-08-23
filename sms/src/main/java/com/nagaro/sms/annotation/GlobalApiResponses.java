package com.nagaro.sms.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nagaro.sms.util.Constants;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ApiResponses(value = {
		@ApiResponse(code = 400, message = Constants.genericRequiredFieldsMissingMsg),
		@ApiResponse(code = 401, message = Constants.authenticationFailed),
		@ApiResponse(code = 403, message = Constants.authorizationFailed),
		@ApiResponse(code = 403, message = Constants.tokenExpired),
		@ApiResponse(code = 500, message = Constants.genericErrorMessage) })
public @interface GlobalApiResponses {
}
