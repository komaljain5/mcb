package com.nagaro.bank.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nagaro.bank.entity.User;
import com.nagaro.bank.exception.RequestProcessingFailedException;
import com.nagaro.bank.repository.UserRepository;
import com.nagaro.bank.service.LoginAttemptsService;
import com.nagaro.bank.util.Constants;

@Service
public class LoginAttemptsServiceImpl implements LoginAttemptsService {

	private static final int MAX_ATTEMPTS_ALLOWED = 3;
	private static final long LOCK_DURATION = 24 * 60 * 60;

	@Autowired
	UserRepository userRepository;

	/**
	 * This method resets the failed login attempt count to zero after a successful
	 * login
	 * 
	 * @param key : username whose login attempts are to be captured
	 */
	@Override
	public void loginSucceeded(String key) {
		Optional<User> user = userRepository.findByUsername(key);
		user.ifPresent(u -> u.setFailedAttempt(0));
		userRepository.save(user.get());
	}

	/**
	 * This method increments the failed login attempt count and locks the user's
	 * account after three failed attempts
	 * 
	 * @param key : username whose login attempts are to be captured
	 */
	@Override
	public void loginFailed(String key) {
		Optional<User> userOpt = userRepository.findByUsername(key);

		if (userOpt.isPresent()) {
			User user = userOpt.get();
			int currentFailAttempts = user.getFailedAttempt();
			if (currentFailAttempts >= MAX_ATTEMPTS_ALLOWED) {
				this.lockAccount(user);
				throw new LockedException(Constants.ACCOUNT_LOCKED_MSG);
			} else {
				user.setFailedAttempt(currentFailAttempts + 1);
				userRepository.save(user);
				throw new BadCredentialsException(Constants.BAD_CRED_MSG);
			}
		}
	}

	/**
	 * This method returns the user account lock status
	 * 
	 * @param user : User whose account status has to be be verified
	 */
	@Override
	public boolean isAccountLocked(String key) {
		Optional<User> userOpt = userRepository.findByUsername(key);
		if (userOpt.isPresent()) {
			return userOpt.get().isAccountLocked();
		} else
			throw new UsernameNotFoundException(key);
	}

	/**
	 * This method locks the user account
	 * 
	 * @param user : User whose account has to be locked
	 */
	public void lockAccount(User user) {
		if (!user.isAccountLocked()) {
			user.setAccountLocked(true);
			user.setLockTime(new Date());
			userRepository.save(user);
		}
	}

	/**
	 * This method unlocks the user account when called
	 * 
	 * @param user : User whose account has to be locked
	 */
	public boolean unlockAccount(User user) {
		long lockTimeInMillis = user.getLockTime().getTime();
		long currentTimeInMillis = System.currentTimeMillis();

		if (lockTimeInMillis + LOCK_DURATION > currentTimeInMillis) {
			user.setAccountLocked(false);
			user.setFailedAttempt(0);
			user.setLockTime(null);

			try {
				userRepository.save(user);
				return true;
			} catch (Exception e) {
				throw new RequestProcessingFailedException(Constants.ATTEMPT_DB_UPDATE_FAILED);
			}
		}
		return false;
	}

}
