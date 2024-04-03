package com.afsd.redis.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sessions")
public class SessionController {
		
	 /**
		 * @author sharath.boyini@npci.org.in
		
		 */
	 	@GetMapping("/create")
	    public String createSession(HttpSession session) {
	        session.setAttribute("sharath", "npci123");
	        return "Session created with userId: sharath";
	    }
	 	
	 	 /**
  		 * @author sharath.boyini@npci.org.in
  		
  		 */
	    @GetMapping("/retrieve")
	    public String retrieveSession(HttpSession session) {
	        Integer userId = (Integer) session.getAttribute("userId");
	        return "User ID from session: " + userId;
	    }
	    
	    /**
  		 * @author sharath.boyini@npci.org.in
  		
  		 */

	    @GetMapping("/delete")
	    public String deleteSession(HttpSession session) {
	        session.invalidate();
	        return "Session deleted";
	    }

}
