package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.Documents;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Documents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentsRepository extends MongoRepository<Documents, String> {
}
