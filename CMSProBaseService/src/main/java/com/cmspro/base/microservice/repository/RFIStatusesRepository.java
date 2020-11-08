package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.RFIStatuses;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RFIStatuses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFIStatusesRepository extends MongoRepository<RFIStatuses, String> {
}
