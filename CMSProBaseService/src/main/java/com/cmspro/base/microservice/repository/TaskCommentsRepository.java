package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.TaskComments;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TaskComments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskCommentsRepository extends MongoRepository<TaskComments, String> {
}
