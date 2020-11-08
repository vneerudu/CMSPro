package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.TaskAttachments;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TaskAttachments} entity.
 */
public interface TaskAttachmentsSearchRepository extends ElasticsearchRepository<TaskAttachments, String> {
}
