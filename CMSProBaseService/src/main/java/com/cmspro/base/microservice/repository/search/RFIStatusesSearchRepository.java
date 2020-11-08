package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.RFIStatuses;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link RFIStatuses} entity.
 */
public interface RFIStatusesSearchRepository extends ElasticsearchRepository<RFIStatuses, String> {
}
