package com.afsd.redis.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.afsd.redis.entity.Author;
import com.afsd.redis.repository.AuthorRepository;
import com.afsd.redis.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
	
	@Mock
    private AuthorRepository authorRepository;

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @InjectMocks
    private AuthorService authorService;

    @Test
    public void testGetAuthorById_CacheHit() throws Exception {
        // Mock data
        Long authorId = 1L;
        Author author = new Author();
        author.setAuthorId(authorId);
        author.setFirstName("John");

        // Mock StringRedisTemplate.opsForValue() separately
        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);

        // Mock Redis cache hit
        when(valueOperations.get("author:" + authorId))
                .thenReturn(new ObjectMapper().writeValueAsString(author));

        // Invoke the method
        Optional<Author> result = authorService.getAuthorById(authorId);

        // Verify that the result is obtained from the cache
        verify(authorRepository, never()).findById(authorId);

        // Assert the result
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getFirstName()).isEqualTo("John");
    }


    @Test
    public void testGetAuthorById_CacheMiss() {
        // Mock data
        Long authorId = 1L;
        Author author = new Author();
        author.setAuthorId(authorId);
        author.setFirstName("John");
        
        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);

        // Mock Redis cache miss
        when(stringRedisTemplate.opsForValue().get("author:" + authorId)).thenReturn(null);

        // Mock database call
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        // Invoke the method
        Optional<Author> result = authorService.getAuthorById(authorId);

        // Verify that the result is obtained from the database
        verify(authorRepository, times(1)).findById(authorId);

        // Assert the result
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getFirstName()).isEqualTo("John");
    }

    @Test
    public void testDeleteAuthor() {
        // Mock data
        Long authorId = 1L;

        // Invoke the method
        authorService.deleteAuthor(authorId);

        // Verify that the cache entry is deleted
        verify(stringRedisTemplate, times(1)).delete("author:" + authorId);
        // Verify that the database deletion is called
        verify(authorRepository, times(1)).deleteById(authorId);
    }

}
