package com.afsd.redis.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.afsd.redis.entity.Publisher;
import com.afsd.redis.repository.PublisherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PublisherService {
	
	 	@Autowired
	    private PublisherRepository publisherRepository;

	    @Autowired
	    private StringRedisTemplate stringRedisTemplate;
	    
	    /**
  		 * @author sharath.boyini@npci.org.in
  		
  		 */
	    @Cacheable(value = "publishers", key = "#id", unless = "#result == null")
	    public Optional<Publisher> getPublisherById(Long id) {
	        // Check the cache first
	        String cachedPublisher = stringRedisTemplate.opsForValue().get("publisher:" + id);

	        if (cachedPublisher != null) {
	            try {
	                // If cached, return the publisher from the cache
	                return Optional.of(new ObjectMapper().readValue(cachedPublisher, Publisher.class));
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	        // If not in cache, fetch from the database
	        Optional<Publisher> publisherOptional = publisherRepository.findById(id);

	        // Put the result in the cache
	        publisherOptional.ifPresent(publisher -> {
	            try {
	                stringRedisTemplate.opsForValue().set("publisher:" + id, new ObjectMapper().writeValueAsString(publisher));
	            } catch (JsonProcessingException e) {
	                e.printStackTrace();
	            }
	        });

	        return publisherOptional;
	    }
	    
	    /**
  		 * @author sharath.boyini@npci.org.in
  		
  		 */

	    @CacheEvict(value = "publishers", key = "#id")
	    public void deletePublisher(Long id) {
	        // Removing from the cache before deleting from the database
	        stringRedisTemplate.delete("publisher:" + id);

	        // Deleting from the database
	        publisherRepository.deleteById(id);
	    }
	    
	    /**
  		 * @author sharath.boyini@npci.org.in
  		
  		 */

	    @Cacheable(value = "allPublishers")
	    public List<Publisher> getAllPublishers() {
	        return publisherRepository.findAll();
	    }
	    
	    /**
  		 * @author sharath.boyini@npci.org.in
  		
  		 */

	    @CacheEvict(value = "publishers", key = "#id")
	    public Publisher updatePublisher(Long id, Publisher updatedPublisher) {
	        Optional<Publisher> existingPublisherOptional = publisherRepository.findById(id);

	        if (existingPublisherOptional.isPresent()) {
	            Publisher existingPublisher = existingPublisherOptional.get();
	            existingPublisher.setPublisherName(updatedPublisher.getPublisherName());
	            existingPublisher.setAddress(updatedPublisher.getAddress());
	            existingPublisher.setPhone(updatedPublisher.getPhone());
	            existingPublisher.setEmail(updatedPublisher.getEmail());

	            // Remove the existing entry from the cache
	            stringRedisTemplate.delete("publisher:" + id);

	            // Save the updated publisher to the database
	            Publisher savedPublisher = publisherRepository.save(existingPublisher);

	            // Cache the updated publisher
	            try {
	                stringRedisTemplate.opsForValue().set("publisher:" + id, new ObjectMapper().writeValueAsString(savedPublisher));
	            } catch (JsonProcessingException e) {
	                e.printStackTrace();
	            }

	            return savedPublisher;
	        }

	        // Handle the case where the publisher with the given id is not found
	        return null;
	    }
	    
	    /**
  		 * @author sharath.boyini@npci.org.in
  		
  		 */

	    @CacheEvict(value = "allPublishers")
	    public Publisher createPublisher(Publisher publisher) {
	        return publisherRepository.save(publisher);
	    }
}
