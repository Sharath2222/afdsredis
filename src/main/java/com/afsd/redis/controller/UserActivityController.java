package com.afsd.redis.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.afsd.redis.entity.UserActivityLogs;
import com.afsd.redis.service.UserActivityService;



public class UserActivityController {
	@Autowired
    private UserActivityService service;
	 /**
	 * @author sharath.boyini@npci.org.in
	 * @apiNote gets all publishers
	 */
    @GetMapping
    public ResponseEntity<List<UserActivityLogs>> getAllUserActivityLogs() {
        List<UserActivityLogs> userActivityLogs = service.getAllUserActivityLogs();
        return ResponseEntity.ok(userActivityLogs);
    }
    /**
	 * @author sharath.boyini@npci.org.in
	 * @apiNote gets all publishers
	 */

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserActivityLogs>> getUserActivityLogsByUserId(@PathVariable UUID userId) {
        List<UserActivityLogs> userActivityLogs = service.getUserActivityLogsByUserId(userId);
        return ResponseEntity.ok(userActivityLogs);
    }
    /**
	 * @author sharath.boyini@npci.org.in
	 * @apiNote gets all publishers
	 */

    @PostMapping
    public ResponseEntity<UserActivityLogs> createUserActivityLog(@RequestBody UserActivityLogs userActivityLog) {
        UserActivityLogs createdUserActivityLog = service.createUserActivityLog(userActivityLog);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserActivityLog);
    }
    /**
	 * @author sharath.boyini@npci.org.in
	 * @apiNote gets all publishers
	 */

    @DeleteMapping("/{userId}/{timestamp}")
    public ResponseEntity<Void> deleteUserActivityLog(@PathVariable UUID userId, @PathVariable LocalDateTime timestamp) {
        service.deleteUserActivityLog(userId, timestamp);
        return ResponseEntity.noContent().build();
    }

}
