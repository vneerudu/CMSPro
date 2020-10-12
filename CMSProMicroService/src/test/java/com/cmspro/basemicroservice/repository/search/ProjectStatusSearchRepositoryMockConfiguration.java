package com.cmspro.basemicroservice.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ProjectStatusSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ProjectStatusSearchRepositoryMockConfiguration {

    @MockBean
    private ProjectStatusSearchRepository mockProjectStatusSearchRepository;

}
