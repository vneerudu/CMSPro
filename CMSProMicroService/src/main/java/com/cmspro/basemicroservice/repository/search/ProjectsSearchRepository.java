package com.cmspro.basemicroservice.repository.search;

import com.cmspro.basemicroservice.domain.Projects;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Projects} entity.
 */
public interface ProjectsSearchRepository extends ElasticsearchRepository<Projects, Long> {
}
