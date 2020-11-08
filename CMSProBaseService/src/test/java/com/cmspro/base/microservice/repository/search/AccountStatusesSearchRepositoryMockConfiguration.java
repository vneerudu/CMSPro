package com.cmspro.base.microservice.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AccountStatusesSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AccountStatusesSearchRepositoryMockConfiguration {

    @MockBean
    private AccountStatusesSearchRepository mockAccountStatusesSearchRepository;

}
