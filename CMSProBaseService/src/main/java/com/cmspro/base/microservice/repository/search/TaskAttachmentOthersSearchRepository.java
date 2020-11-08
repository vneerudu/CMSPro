package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.TaskAttachmentOthers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TaskAttachmentOthers} entity.
 */
public interface TaskAttachmentOthersSearchRepository extends ElasticsearchRepository<TaskAttachmentOthers, String> {
}
