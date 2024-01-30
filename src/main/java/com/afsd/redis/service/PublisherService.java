package com.afsd.redis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afsd.redis.entity.Publisher;
import com.afsd.redis.repository.PublisherRepository;

@Service
public class PublisherService {
	
	 @Autowired
	    private PublisherRepository publisherRepository;

	    public List<Publisher> getAllPublishers() {
	        return publisherRepository.findAll();
	    }

	    public Optional<Publisher> getPublisherById(Long id) {
	        return publisherRepository.findById(id);
	    }

	    public Publisher createPublisher(Publisher publisher) {
	        return publisherRepository.save(publisher);
	    }

	    public Publisher updatePublisher(Long id, Publisher updatedPublisher) {
	        Optional<Publisher> existingPublisherOptional = publisherRepository.findById(id);

	        if (existingPublisherOptional.isPresent()) {
	            Publisher existingPublisher = existingPublisherOptional.get();
	            existingPublisher.setPublisherName(updatedPublisher.getPublisherName());
	            existingPublisher.setAddress(updatedPublisher.getAddress());
	            existingPublisher.setPhone(updatedPublisher.getPhone());
	            existingPublisher.setEmail(updatedPublisher.getEmail());

	            return publisherRepository.save(existingPublisher);
	        }

	        // Handle the case where the publisher with the given id is not found
	        return null;
	    }

	    public void deletePublisher(Long id) {
	        publisherRepository.deleteById(id);
	    }

}
