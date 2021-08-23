package com.nagaro.bank.service;

import org.springframework.stereotype.Service;

import com.nagaro.bank.entity.User;

@Service
public interface LoginAttemptsService {
		     	   
	    public void loginSucceeded(String key);

	    public void loginFailed(String key) ;
	    
	    public boolean isAccountLocked(String key);
	    
	    public void lockAccount(User user);
	    
	    public boolean unlockAccount(User user);

}
