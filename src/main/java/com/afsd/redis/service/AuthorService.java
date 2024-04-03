package com.afsd.redis.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.afsd.redis.entity.Author;
import com.afsd.redis.repository.AuthorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthorService {
	
	
	 	@Autowired
	    private AuthorRepository authorRepository;
	 	
	 	 @Autowired
	     private StringRedisTemplate stringRedisTemplate;
	 	 
	 	 /**
	  		 * @author sharath.boyini@npci.org.in
	  		
	  		 */
	 	 @Cacheable(value = "authors", key = "#id", unless = "#result == null")
	     public Optional<Author> getAuthorById(Long id) {
	         // Check the cache first
	         String cachedAuthor = stringRedisTemplate.opsForValue().get("author:" + id);

	         if (cachedAuthor != null) {
	             try {
	                 // If cached, return the author from the cache
	                 return Optional.of(new ObjectMapper().readValue(cachedAuthor, Author.class));
	             } catch (IOException e) {
	                 e.printStackTrace();
	             }
	         }

	         // If not in cache, fetch from the database
	         Optional<Author> authorOptional = authorRepository.findById(id);

	         // Put the result in the cache
	         authorOptional.ifPresent(author -> {
	             try {
	                 stringRedisTemplate.opsForValue().set("author:" + id, new ObjectMapper().writeValueAsString(author));
	             } catch (JsonProcessingException e) {
	                 e.printStackTrace();
	             }
	         });

	         return authorOptional;
	     }

	 	 /**
	  		 * @author sharath.boyini@npci.org.in
	  		
	  		 */
	     @CacheEvict(value = "authors", key = "#id")
	     public void deleteAuthor(Long id) {
	         // Removing from the cache before deleting from the database
	         stringRedisTemplate.delete("author:" + id);

	         // Deleting from the database
	         authorRepository.deleteById(id);
	     }
	     
	     /**
	  		 * @author sharath.boyini@npci.org.in
	  		
	  		 */

	     public List<Author> getAllAuthors() {
	         return authorRepository.findAll();
	     }
	     
	     /**
	  		 * @author sharath.boyini@npci.org.in
	  		
	  		 */
	     public Author createAuthor(Author author) {
	         return authorRepository.save(author);
	     }
	     
	     /**
	  		 * @author sharath.boyini@npci.org.in
	  		
	  		 */

	     public Author updateAuthor(Long id, Author updatedAuthor) {
	         Optional<Author> existingAuthorOptional = authorRepository.findById(id);

	         if (existingAuthorOptional.isPresent()) {
	             Author existingAuthor = existingAuthorOptional.get();
	             existingAuthor.setFirstName(updatedAuthor.getFirstName());
	             existingAuthor.setLastName(updatedAuthor.getLastName());
	             existingAuthor.setBirthDate(updatedAuthor.getBirthDate());
	             existingAuthor.setCountry(updatedAuthor.getCountry());
	             existingAuthor.setAwards(updatedAuthor.getAwards());

	             // Remove the existing entry from the cache
	             stringRedisTemplate.delete("author:" + id);

	             // Save the updated author to the database
	             Author savedAuthor = authorRepository.save(existingAuthor);

	             // Cache the updated author
	             try {
	                 stringRedisTemplate.opsForValue().set("author:" + id, new ObjectMapper().writeValueAsString(savedAuthor));
	             } catch (JsonProcessingException e) {
	                 e.printStackTrace(); 
	             }

	             return savedAuthor;
	         }

	         // Handle the case where the author with the given id is not found
	         return null;
	     }
}
