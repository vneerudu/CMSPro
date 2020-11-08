package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.Sheets;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Sheets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SheetsRepository extends MongoRepository<Sheets, String> {
}
