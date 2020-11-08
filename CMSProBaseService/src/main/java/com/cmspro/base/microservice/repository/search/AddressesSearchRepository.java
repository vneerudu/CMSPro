package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.Addresses;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Addresses} entity.
 */
public interface AddressesSearchRepository extends ElasticsearchRepository<Addresses, String> {
}
