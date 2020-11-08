package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.TaskAttachmentImages;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TaskAttachmentImages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskAttachmentImagesRepository extends MongoRepository<TaskAttachmentImages, String> {
}
