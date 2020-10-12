package com.cmspro.basemicroservice.repository.search;

import com.cmspro.basemicroservice.domain.ProjectStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProjectStatus} entity.
 */
public interface ProjectStatusSearchRepository extends ElasticsearchRepository<ProjectStatus, Long> {
}
