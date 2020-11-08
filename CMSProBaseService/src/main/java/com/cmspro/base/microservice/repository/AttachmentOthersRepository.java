package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.AttachmentOthers;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the AttachmentOthers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentOthersRepository extends MongoRepository<AttachmentOthers, String> {
}
