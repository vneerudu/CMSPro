package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.TaskAttachmentImages;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TaskAttachmentImages} entity.
 */
public interface TaskAttachmentImagesSearchRepository extends ElasticsearchRepository<TaskAttachmentImages, String> {
}
