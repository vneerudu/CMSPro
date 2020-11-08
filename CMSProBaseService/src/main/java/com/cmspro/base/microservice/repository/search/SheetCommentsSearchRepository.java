package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.SheetComments;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link SheetComments} entity.
 */
public interface SheetCommentsSearchRepository extends ElasticsearchRepository<SheetComments, String> {
}
