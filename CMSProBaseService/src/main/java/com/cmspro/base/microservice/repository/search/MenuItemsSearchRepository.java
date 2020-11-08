package com.cmspro.base.microservice.repository.search;

import com.cmspro.base.microservice.domain.MenuItems;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link MenuItems} entity.
 */
public interface MenuItemsSearchRepository extends ElasticsearchRepository<MenuItems, String> {
}
