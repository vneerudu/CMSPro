package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.SheetTags;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SheetTags entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SheetTagsRepository extends MongoRepository<SheetTags, String> {
}
