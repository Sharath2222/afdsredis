package com.afsd.redis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.afsd.redis.entity.BookRecommendation;
import com.afsd.redis.entity.UserActivityLogs;
import com.afsd.redis.repository.UserActivity;
import com.afsd.redis.service.BookRecommendationService;
import com.afsd.redis.service.UserActivityService;

public class BookRecommendationController {
	
	 @Autowired
	    private BookRecommendationService service;
	 /**
		 * @author sharath.boyini@npci.org.in
		 * @apiNote gets all publishers
		 */

	    @GetMapping
	    public ResponseEntity<List<BookRecommendation>> getAllBookRecommendations() {
	        List<BookRecommendation> bookRecommendations = service.getAllBookRecommendations();
	        return ResponseEntity.ok(bookRecommendations);
	    }
	    /**
		 * @author sharath.boyini@npci.org.in
		 * @apiNote gets all publishers
		 */

	    @GetMapping("/{userId}")
	    public ResponseEntity<List<BookRecommendation>> getBookRecommendationsByUserId(@PathVariable UUID userId) {
	        List<BookRecommendation> bookRecommendations = service.getBookRecommendationsByUserId(userId);
	        return ResponseEntity.ok(bookRecommendations);
	    }
	    /**
		 * @author sharath.boyini@npci.org.in
		 * @apiNote gets all publishers
		 */

	    @PostMapping
	    public ResponseEntity<BookRecommendation> createBookRecommendation(@RequestBody BookRecommendation bookRecommendation) {
	        BookRecommendation createdBookRecommendation = service.createBookRecommendation(bookRecommendation);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdBookRecommendation);
	    }
	    /**
		 * @author sharath.boyini@npci.org.in
		 * @apiNote gets all publishers
		 */

	    @DeleteMapping("/{userId}/{bookId}")
	    public ResponseEntity<Void> deleteBookRecommendation(@PathVariable UUID userId, @PathVariable UUID bookId) {
	        service.deleteBookRecommendation(userId, bookId);
	        return ResponseEntity.noContent().build();
	    }
	

}
