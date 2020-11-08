package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.Users;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Users} entity.
 */
public interface UsersSearchRepository extends ElasticsearchRepository<Users, String> {
}
