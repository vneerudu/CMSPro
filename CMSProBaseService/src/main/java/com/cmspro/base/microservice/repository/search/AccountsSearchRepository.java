package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.Accounts;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Accounts} entity.
 */
public interface AccountsSearchRepository extends ElasticsearchRepository<Accounts, String> {
}
