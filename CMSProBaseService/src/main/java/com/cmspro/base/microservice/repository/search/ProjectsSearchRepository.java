package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.Projects;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Projects} entity.
 */
public interface ProjectsSearchRepository extends ElasticsearchRepository<Projects, String> {
}
