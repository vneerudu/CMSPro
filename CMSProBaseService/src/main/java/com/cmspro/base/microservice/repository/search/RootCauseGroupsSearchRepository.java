package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.RootCauseGroups;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link RootCauseGroups} entity.
 */
public interface RootCauseGroupsSearchRepository extends ElasticsearchRepository<RootCauseGroups, String> {
}
