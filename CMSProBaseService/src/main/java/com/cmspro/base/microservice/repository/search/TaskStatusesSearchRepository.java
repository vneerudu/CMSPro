package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.TaskStatuses;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TaskStatuses} entity.
 */
public interface TaskStatusesSearchRepository extends ElasticsearchRepository<TaskStatuses, String> {
}
