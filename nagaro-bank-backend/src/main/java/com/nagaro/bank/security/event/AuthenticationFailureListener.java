package com.nagaro.bank.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.nagaro.bank.service.LoginAttemptsService;

@Component
public class AuthenticationFailureListener implements ApplicationListener < AuthenticationFailureBadCredentialsEvent > {

    private static Logger LOG = LoggerFactory.getLogger(AuthenticationFailureListener.class);

	@Autowired
	private LoginAttemptsService loginAttemptsService;
	
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getName();
        LOG.info("********* login failed for user {} ", username);
        loginAttemptsService.loginFailed(username);
    }

}