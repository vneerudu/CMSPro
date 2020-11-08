package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.TaskAttachments;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TaskAttachments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskAttachmentsRepository extends MongoRepository<TaskAttachments, String> {
}
