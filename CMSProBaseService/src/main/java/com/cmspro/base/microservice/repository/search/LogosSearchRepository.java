package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.Logos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Logos} entity.
 */
public interface LogosSearchRepository extends ElasticsearchRepository<Logos, String> {
}
