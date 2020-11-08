package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.SheetTags;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link SheetTags} entity.
 */
public interface SheetTagsSearchRepository extends ElasticsearchRepository<SheetTags, String> {
}
