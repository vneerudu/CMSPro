package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.RFITimeLine;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RFITimeLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFITimeLineRepository extends MongoRepository<RFITimeLine, String> {
}
