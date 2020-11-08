package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.AccountStatuses;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AccountStatuses} entity.
 */
public interface AccountStatusesSearchRepository extends ElasticsearchRepository<AccountStatuses, String> {
}
