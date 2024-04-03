package com.afsd.redis.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.afsd.redis.entity.Publisher;
import com.afsd.redis.repository.PublisherRepository;
import com.afsd.redis.service.PublisherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {
	
	 	@Mock
	    private PublisherRepository publisherRepository;

	    @Mock
	    private StringRedisTemplate stringRedisTemplate;

	    @InjectMocks
	    private PublisherService publisherService;

	    @Test
	    public void testGetPublisherById_CacheHit() throws JsonProcessingException {
	        // Mock data
	        Long publisherId = 1L;
	        Publisher publisher = new Publisher();
	        publisher.setPublisherId(publisherId);
	        publisher.setPublisherName("Publisher1");
	        
	        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
	        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);


	        when(stringRedisTemplate.opsForValue().get("publisher:" + publisherId))
	                .thenReturn(new ObjectMapper().writeValueAsString(publisher));

	        // Invoke the method
	        Optional<Publisher> result = publisherService.getPublisherById(publisherId);

	        // Verify that the result is obtained from the cache
	        verify(publisherRepository, times(0)).findById(publisherId);

	        // Assert the result
	        assertThat(result.isPresent()).isTrue();
	        assertThat(result.get().getPublisherName()).isEqualTo("Publisher1");
	    }

	    @Test
	    public void testGetPublisherById_CacheMiss() throws JsonProcessingException {
	        // Mock data
	        Long publisherId = 1L;
	        Publisher publisher = new Publisher();
	        publisher.setPublisherId(publisherId);
	        publisher.setPublisherName("Publisher1");
	        
	        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
	        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
	        // Mock Redis cache miss
	        when(stringRedisTemplate.opsForValue().get("publisher:" + publisherId)).thenReturn(null);

	        // Mock database call
	        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(publisher));

	        // Invoke the method
	        Optional<Publisher> result = publisherService.getPublisherById(publisherId);

	        // Verify that the result is obtained from the database
	        verify(publisherRepository, times(1)).findById(publisherId);

	        // Assert the result
	        assertThat(result.isPresent()).isTrue();
	        assertThat(result.get().getPublisherName()).isEqualTo("Publisher1");
	    }

	    @Test
	    public void testDeletePublisher() {
	        // Mock data
	        Long publisherId = 1L;

	        // Invoke the method
	        publisherService.deletePublisher(publisherId);

	        // Verify that the cache entry is deleted
	        verify(stringRedisTemplate, times(1)).delete("publisher:" + publisherId);
	        // Verify that the database deletion is called
	        verify(publisherRepository, times(1)).deleteById(publisherId);
	    }

}
