package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.SheetHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link SheetHistory} entity.
 */
public interface SheetHistorySearchRepository extends ElasticsearchRepository<SheetHistory, String> {
}
