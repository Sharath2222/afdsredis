package com.afsd.redis.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afsd.redis.entity.BookRecommendation;

import antlr.collections.List;

@Service
public class BookRecommendationService {
	
	 @Autowired
	    private BookRecommendation repository;

	    public List<BookRecommendation> getAllBookRecommendations() {
	        return repository.findAll();
	    }

	    public List<BookRecommendation> getBookRecommendationsByUserId(UUID userId) {
	        return repository.findByUserIdOrderByScoreDesc(userId);
	    }

	    public BookRecommendation createBookRecommendation(BookRecommendation bookRecommendation) {
	        return repository.save(bookRecommendation);
	    }

	    public void deleteBookRecommendation(UUID userId, UUID bookId) {
	        repository.deleteByUserIdAndBookId(userId, bookId);
	    }

}
