package com.afsd.redis.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class SessionService {
	
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

	
	
	private static final long INACTIVITY_TIMEOUT_SECONDS = 300; 
	
	 /**
		 * @author sharath.boyini@npci.org.in
		
		 */
    @CachePut(value = "userActivityCache", key = "#sessionId")
    public Long updateLastActivityTimestamp(String sessionId) {
        return System.currentTimeMillis();
    }
    
    /**
		 * @author sharath.boyini@npci.org.in
		
		 */

    @Cacheable(value = "userActivityCache", key = "#sessionId")
    public Long getLastActivityTimestamp(String sessionId) {
        return System.currentTimeMillis();
    }
    
    /**
		 * @author sharath.boyini@npci.org.in
		
		 */

    @CacheEvict(value = "userActivityCache", key = "#sessionId")
    public void invalidateUserSession(String sessionId) {
        logger.info("user is invalidated"); 
    }
    
    /**
		 * @author sharath.boyini@npci.org.in
		
		 */
    
    @CacheEvict(value = "userActivityCache", key = "#sessionId")
    public void checkAndInvalidateIfInactive(String sessionId) {
        long lastActivityTimestamp = getLastActivityTimestamp(sessionId);

        if (System.currentTimeMillis() - lastActivityTimestamp > (INACTIVITY_TIMEOUT_SECONDS * 1000)) {
            invalidateUserSession(sessionId);
        }
    }	

}
