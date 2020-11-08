package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.RFI;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link RFI} entity.
 */
public interface RFISearchRepository extends ElasticsearchRepository<RFI, String> {
}
