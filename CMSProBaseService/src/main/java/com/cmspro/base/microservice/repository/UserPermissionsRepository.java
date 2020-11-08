package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.UserPermissions;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the UserPermissions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPermissionsRepository extends MongoRepository<UserPermissions, String> {
}
