package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.UserGroups;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the UserGroups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserGroupsRepository extends MongoRepository<UserGroups, String> {
}
