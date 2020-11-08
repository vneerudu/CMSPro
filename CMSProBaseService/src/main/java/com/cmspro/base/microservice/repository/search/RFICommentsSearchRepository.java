package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.RFIComments;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link RFIComments} entity.
 */
public interface RFICommentsSearchRepository extends ElasticsearchRepository<RFIComments, String> {
}
