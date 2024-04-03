package com.afsd.redis.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserActivity extends CassandraRepository<BookRecommendation, UUID>{

}
