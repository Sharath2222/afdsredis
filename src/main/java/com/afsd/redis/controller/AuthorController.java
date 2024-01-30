package com.afsd.redis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afsd.redis.entity.Author;
import com.afsd.redis.service.AuthorService;


@RestController
@RequestMapping("/authors")
public class AuthorController {
	
	  @Autowired
	    private AuthorService authorService;

	    @GetMapping
	    public List<Author> getAllAuthors() {
	        return authorService.getAllAuthors();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
	        Optional<Author> author = authorService.getAuthorById(id);

	        return author.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
	                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    @PostMapping
	    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
	        Author createdAuthor = authorService.createAuthor(author);

	        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor) {
	        Author updated = authorService.updateAuthor(id, updatedAuthor);

	        return Optional.ofNullable(updated)
	                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
	                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
	        authorService.deleteAuthor(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

}
