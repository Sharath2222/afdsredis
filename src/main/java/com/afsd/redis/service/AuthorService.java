package com.afsd.redis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afsd.redis.entity.Author;
import com.afsd.redis.repository.AuthorRepository;

@Service
public class AuthorService {
	
	
	 	@Autowired
	    private AuthorRepository authorRepository;

	    public List<Author> getAllAuthors() {
	        return authorRepository.findAll();
	    }

	    public Optional<Author> getAuthorById(Long id) {
	        return authorRepository.findById(id);
	    }

	    public Author createAuthor(Author author) {
	        return authorRepository.save(author);
	    }

	    public Author updateAuthor(Long id, Author updatedAuthor) {
	        Optional<Author> existingAuthorOptional = authorRepository.findById(id);

	        if (existingAuthorOptional.isPresent()) {
	            Author existingAuthor = existingAuthorOptional.get();
	            existingAuthor.setFirstName(updatedAuthor.getFirstName());
	            existingAuthor.setLastName(updatedAuthor.getLastName());
	            existingAuthor.setBirthDate(updatedAuthor.getBirthDate());
	            existingAuthor.setCountry(updatedAuthor.getCountry());
	            existingAuthor.setAwards(updatedAuthor.getAwards());
	            
	            return authorRepository.save(existingAuthor);
	        }

	        // Handle the case where the author with the given id is not found
	        return null;
	    }

	    public void deleteAuthor(Long id) {
	        authorRepository.deleteById(id);
	    }

}
