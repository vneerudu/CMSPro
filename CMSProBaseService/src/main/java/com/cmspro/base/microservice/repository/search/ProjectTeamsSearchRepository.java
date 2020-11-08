package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.ProjectTeams;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ProjectTeams} entity.
 */
public interface ProjectTeamsSearchRepository extends ElasticsearchRepository<ProjectTeams, String> {
}
