package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.TaskStatuses;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TaskStatuses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskStatusesRepository extends MongoRepository<TaskStatuses, String> {
}
