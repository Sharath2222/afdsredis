package com.afsd.redis.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afsd.redis.entity.UserActivityLogs;
import com.afsd.redis.repository.UserActivity;

@Service
public class UserActivityService {
	
	 	@Autowired
	    private UserActivity repository;

	    public List<UserActivityLogs> getAllUserActivityLogs() {
	        return repository.findAll();
	    }

	    public List<UserActivityLogs> getUserActivityLogsByUserId(UUID userId) {
	        return repository.findByUserId(userId);
	    }

	    public UserActivityLogs createUserActivityLog(UserActivityLogs userActivityLog) {
	        return repository.save(userActivityLogs);
	    }

	    public void deleteUserActivityLog(UUID userId, LocalDateTime timestamp) {
	        repository.deleteById(new UserActivity(userId, timestamp));
	    }

}
