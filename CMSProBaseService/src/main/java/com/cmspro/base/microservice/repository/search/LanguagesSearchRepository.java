package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.Languages;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Languages} entity.
 */
public interface LanguagesSearchRepository extends ElasticsearchRepository<Languages, String> {
}
