package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.States;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the States entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatesRepository extends MongoRepository<States, String> {
}
