package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.AttachmentOthers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AttachmentOthers} entity.
 */
public interface AttachmentOthersSearchRepository extends ElasticsearchRepository<AttachmentOthers, String> {
}
