package com.afsd.redis.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface BookRecommendation extends CassandraRepository<UserActivityLog, UUID>{

}
