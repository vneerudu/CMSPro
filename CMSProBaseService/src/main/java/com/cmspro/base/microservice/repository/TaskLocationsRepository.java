package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.TaskLocations;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TaskLocations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskLocationsRepository extends MongoRepository<TaskLocations, String> {
}
