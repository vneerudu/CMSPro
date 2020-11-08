package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.AddressTypes;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the AddressTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressTypesRepository extends MongoRepository<AddressTypes, String> {
}
