package com.afsd.redis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.keyvalue.core.mapping.KeySpaceResolver;

@Configuration
public class RateLimitConfiguration {

    @Bean
    public KeySpaceResolver apiKeyResolver() {
        // Use API key for rate limiting
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("api-key"));
    }
}
