package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.Languages;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Languages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LanguagesRepository extends MongoRepository<Languages, String> {
}
