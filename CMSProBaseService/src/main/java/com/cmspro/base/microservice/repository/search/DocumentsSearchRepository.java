package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.Documents;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Documents} entity.
 */
public interface DocumentsSearchRepository extends ElasticsearchRepository<Documents, String> {
}
