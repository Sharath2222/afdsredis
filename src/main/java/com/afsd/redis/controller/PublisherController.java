package com.afsd.redis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afsd.redis.entity.Publisher;
import com.afsd.redis.service.PublisherService;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
	
	 @Autowired
	    private PublisherService publisherService;
	 	
	 	
	 	/**
		 * @author sharath.boyini@npci.org.in
		 * @apiNote gets all publishers
		 *@param id
		 */
	    @GetMapping
	    public List<Publisher> getAllPublishers() {
	        return publisherService.getAllPublishers();
	    }
	    
	    /**
  		 * @author sharath.boyini@npci.org.in
  		 * @apiNote gets all authors by id
  		 *@param id
  		 */

	    @GetMapping("/{id}")
	    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id) {
	        Optional<Publisher> publisher = publisherService.getPublisherById(id);

	        return publisher.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
	                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }
	    
	    /**
  		 * @author sharath.boyini@npci.org.in
  		 * @apiNote posts all authors
  		 *@param id
  		 */

	    @PostMapping
	    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
	        Publisher createdPublisher = publisherService.createPublisher(publisher);

	        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long id, @RequestBody Publisher updatedPublisher) {
	        Publisher updated = publisherService.updatePublisher(id, updatedPublisher);

	        return Optional.ofNullable(updated)
	                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
	                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }
	    
	    /**
  		 * @author sharath.boyini@npci.org.in
  		 * @apiNote gets all publishers
  		 *@param id
  		 */

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
	        publisherService.deletePublisher(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

}
