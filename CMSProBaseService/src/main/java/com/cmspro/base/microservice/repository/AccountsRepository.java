package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.Accounts;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Accounts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountsRepository extends MongoRepository<Accounts, String> {
}
