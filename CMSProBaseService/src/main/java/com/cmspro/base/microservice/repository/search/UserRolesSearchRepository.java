package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.UserRoles;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link UserRoles} entity.
 */
public interface UserRolesSearchRepository extends ElasticsearchRepository<UserRoles, String> {
}
