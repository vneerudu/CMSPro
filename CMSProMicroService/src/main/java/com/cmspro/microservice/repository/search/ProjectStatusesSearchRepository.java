package com.cmspro.microservice.repository.search;

import com.cmspro.microservice.domain.ProjectStatuses;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ProjectStatuses} entity.
 */
public interface ProjectStatusesSearchRepository extends ElasticsearchRepository<ProjectStatuses, Long> {
}
