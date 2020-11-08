package com.cmspro.base.microservice.repository;

import com.cmspro.base.microservice.domain.MenuItems;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the MenuItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuItemsRepository extends MongoRepository<MenuItems, String> {
}
