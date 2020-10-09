package com.cmspro.microservice.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ProjectStatusesSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ProjectStatusesSearchRepositoryMockConfiguration {

    @MockBean
    private ProjectStatusesSearchRepository mockProjectStatusesSearchRepository;

}
