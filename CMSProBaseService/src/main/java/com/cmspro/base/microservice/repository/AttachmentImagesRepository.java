package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.AttachmentImages;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the AttachmentImages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentImagesRepository extends MongoRepository<AttachmentImages, String> {
}
