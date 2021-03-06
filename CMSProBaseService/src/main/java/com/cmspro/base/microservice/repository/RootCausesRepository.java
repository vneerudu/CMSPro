package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.RootCauses;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RootCauses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RootCausesRepository extends MongoRepository<RootCauses, String> {
}
