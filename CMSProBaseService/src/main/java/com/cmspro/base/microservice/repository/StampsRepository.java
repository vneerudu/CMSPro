package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.Stamps;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Stamps entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StampsRepository extends MongoRepository<Stamps, String> {
}
