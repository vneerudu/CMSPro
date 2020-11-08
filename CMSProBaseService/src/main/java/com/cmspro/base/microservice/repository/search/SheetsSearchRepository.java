package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.Sheets;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Sheets} entity.
 */
public interface SheetsSearchRepository extends ElasticsearchRepository<Sheets, String> {
}
