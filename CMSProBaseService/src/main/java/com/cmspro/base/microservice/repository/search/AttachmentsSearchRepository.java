package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.Attachments;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Attachments} entity.
 */
public interface AttachmentsSearchRepository extends ElasticsearchRepository<Attachments, String> {
}
