package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.Tasks;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Tasks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TasksRepository extends MongoRepository<Tasks, String> {
}
