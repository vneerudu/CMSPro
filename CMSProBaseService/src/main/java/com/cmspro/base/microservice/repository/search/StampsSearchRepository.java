package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.Stamps;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Stamps} entity.
 */
public interface StampsSearchRepository extends ElasticsearchRepository<Stamps, String> {
}
