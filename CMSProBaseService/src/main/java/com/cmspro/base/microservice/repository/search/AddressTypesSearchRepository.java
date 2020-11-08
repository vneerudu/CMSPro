package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.AddressTypes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AddressTypes} entity.
 */
public interface AddressTypesSearchRepository extends ElasticsearchRepository<AddressTypes, String> {
}
