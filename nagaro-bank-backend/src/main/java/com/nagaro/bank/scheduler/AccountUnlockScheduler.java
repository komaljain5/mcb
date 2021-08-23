package com.nagaro.bank.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nagaro.bank.entity.User;
import com.nagaro.bank.repository.UserRepository;
import com.nagaro.bank.service.LoginAttemptsService;

/**
 * This is a cron job running at an interval of 12 hours
 * to unlock the user accounts which have been locked over 24 hours
 * @author komaljain01
 *
 */
@Component
public class AccountUnlockScheduler {
	
	@Autowired
	LoginAttemptsService loginAttemptsService;
	
	@Autowired
	UserRepository userRepository;
	
	@Scheduled(fixedRate = 12*60*60)
	public void scheduleFixedRateTask() {
		List<User> usersList = userRepository.findByAccountLockedTrue();
		usersList.forEach(user -> loginAttemptsService.unlockAccount(user));
	}

}
