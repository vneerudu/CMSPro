package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.RFITimeLine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link RFITimeLine} entity.
 */
public interface RFITimeLineSearchRepository extends ElasticsearchRepository<RFITimeLine, String> {
}
