package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.AccountStatuses;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the AccountStatuses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountStatusesRepository extends MongoRepository<AccountStatuses, String> {
}
