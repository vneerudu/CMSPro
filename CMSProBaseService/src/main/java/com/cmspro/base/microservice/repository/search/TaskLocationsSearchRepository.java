package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.TaskLocations;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TaskLocations} entity.
 */
public interface TaskLocationsSearchRepository extends ElasticsearchRepository<TaskLocations, String> {
}
