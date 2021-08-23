package com.nagaro.bank.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.nagaro.bank.service.LoginAttemptsService;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	private static Logger LOG = LoggerFactory.getLogger(AuthenticationSuccessListener.class);

	@Autowired
	private LoginAttemptsService loginAttemptsService;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		String username = event.getAuthentication().getName();
		LOG.info("********* login successful for user {} ", username);
		loginAttemptsService.loginSucceeded(username);
	}
}
