package com.afsd.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afsd.redis.entity.Author;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{

}
