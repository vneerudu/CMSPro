package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.TaskAttachmentOthers;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TaskAttachmentOthers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskAttachmentOthersRepository extends MongoRepository<TaskAttachmentOthers, String> {
}
