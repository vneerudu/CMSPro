package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.RFI;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RFI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFIRepository extends MongoRepository<RFI, String> {
}
