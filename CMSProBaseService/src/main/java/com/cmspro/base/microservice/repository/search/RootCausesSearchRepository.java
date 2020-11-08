package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.RootCauses;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link RootCauses} entity.
 */
public interface RootCausesSearchRepository extends ElasticsearchRepository<RootCauses, String> {
}
