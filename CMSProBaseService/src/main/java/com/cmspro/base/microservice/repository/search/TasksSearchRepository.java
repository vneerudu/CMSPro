package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.Tasks;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Tasks} entity.
 */
public interface TasksSearchRepository extends ElasticsearchRepository<Tasks, String> {
}
