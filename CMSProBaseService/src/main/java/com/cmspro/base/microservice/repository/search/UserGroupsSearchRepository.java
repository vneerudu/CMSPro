package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.UserGroups;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link UserGroups} entity.
 */
public interface UserGroupsSearchRepository extends ElasticsearchRepository<UserGroups, String> {
}
