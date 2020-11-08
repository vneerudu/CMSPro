package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.RootCauseGroups;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RootCauseGroups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RootCauseGroupsRepository extends MongoRepository<RootCauseGroups, String> {
}
