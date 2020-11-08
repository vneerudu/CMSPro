package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.UserPermissions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link UserPermissions} entity.
 */
public interface UserPermissionsSearchRepository extends ElasticsearchRepository<UserPermissions, String> {
}
