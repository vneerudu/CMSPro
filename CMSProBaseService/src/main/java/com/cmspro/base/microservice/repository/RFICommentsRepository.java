package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.RFIComments;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RFIComments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFICommentsRepository extends MongoRepository<RFIComments, String> {
}
