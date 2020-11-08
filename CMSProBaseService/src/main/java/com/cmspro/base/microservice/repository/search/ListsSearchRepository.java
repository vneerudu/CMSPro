package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.Lists;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Lists} entity.
 */
public interface ListsSearchRepository extends ElasticsearchRepository<Lists, String> {
}
