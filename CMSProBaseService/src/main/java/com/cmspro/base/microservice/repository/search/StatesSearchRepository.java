package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.States;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link States} entity.
 */
public interface StatesSearchRepository extends ElasticsearchRepository<States, String> {
}
