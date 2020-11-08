package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.AttachmentImages;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AttachmentImages} entity.
 */
public interface AttachmentImagesSearchRepository extends ElasticsearchRepository<AttachmentImages, String> {
}
