package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.Logos;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Logos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogosRepository extends MongoRepository<Logos, String> {
}
