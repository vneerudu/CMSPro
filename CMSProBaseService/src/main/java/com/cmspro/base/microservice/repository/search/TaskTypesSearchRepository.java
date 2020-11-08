package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.TaskTypes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TaskTypes} entity.
 */
public interface TaskTypesSearchRepository extends ElasticsearchRepository<TaskTypes, String> {
}
