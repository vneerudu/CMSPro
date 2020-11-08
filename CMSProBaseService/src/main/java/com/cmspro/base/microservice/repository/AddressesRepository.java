package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.Addresses;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Addresses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressesRepository extends MongoRepository<Addresses, String> {
}
