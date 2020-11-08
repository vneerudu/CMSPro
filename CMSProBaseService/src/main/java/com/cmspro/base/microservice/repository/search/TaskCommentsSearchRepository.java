package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.TaskComments;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TaskComments} entity.
 */
public interface TaskCommentsSearchRepository extends ElasticsearchRepository<TaskComments, String> {
}
