package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.AccountStatuses;
import com.cmspro.base.microservice.repository.AccountStatusesRepository;
import com.cmspro.base.microservice.repository.search.AccountStatusesSearchRepository;
import com.cmspro.base.microservice.service.AccountStatusesService;
import com.cmspro.base.microservice.service.dto.AccountStatusesDTO;
import com.cmspro.base.microservice.service.mapper.AccountStatusesMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AccountStatusesResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccountStatusesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private AccountStatusesRepository accountStatusesRepository;

    @Autowired
    private AccountStatusesMapper accountStatusesMapper;

    @Autowired
    private AccountStatusesService accountStatusesService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.AccountStatusesSearchRepositoryMockConfiguration
     */
    @Autowired
    private AccountStatusesSearchRepository mockAccountStatusesSearchRepository;

    @Autowired
    private MockMvc restAccountStatusesMockMvc;

    private AccountStatuses accountStatuses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountStatuses createEntity() {
        AccountStatuses accountStatuses = new AccountStatuses()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return accountStatuses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountStatuses createUpdatedEntity() {
        AccountStatuses accountStatuses = new AccountStatuses()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return accountStatuses;
    }

    @BeforeEach
    public void initTest() {
        accountStatusesRepository.deleteAll();
        accountStatuses = createEntity();
    }

    @Test
    public void createAccountStatuses() throws Exception {
        int databaseSizeBeforeCreate = accountStatusesRepository.findAll().size();
        // Create the AccountStatuses
        AccountStatusesDTO accountStatusesDTO = accountStatusesMapper.toDto(accountStatuses);
        restAccountStatusesMockMvc.perform(post("/api/account-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountStatusesDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountStatuses in the database
        List<AccountStatuses> accountStatusesList = accountStatusesRepository.findAll();
        assertThat(accountStatusesList).hasSize(databaseSizeBeforeCreate + 1);
        AccountStatuses testAccountStatuses = accountStatusesList.get(accountStatusesList.size() - 1);
        assertThat(testAccountStatuses.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAccountStatuses.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAccountStatuses.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the AccountStatuses in Elasticsearch
        verify(mockAccountStatusesSearchRepository, times(1)).save(testAccountStatuses);
    }

    @Test
    public void createAccountStatusesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountStatusesRepository.findAll().size();

        // Create the AccountStatuses with an existing ID
        accountStatuses.setId("existing_id");
        AccountStatusesDTO accountStatusesDTO = accountStatusesMapper.toDto(accountStatuses);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountStatusesMockMvc.perform(post("/api/account-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountStatusesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountStatuses in the database
        List<AccountStatuses> accountStatusesList = accountStatusesRepository.findAll();
        assertThat(accountStatusesList).hasSize(databaseSizeBeforeCreate);

        // Validate the AccountStatuses in Elasticsearch
        verify(mockAccountStatusesSearchRepository, times(0)).save(accountStatuses);
    }


    @Test
    public void getAllAccountStatuses() throws Exception {
        // Initialize the database
        accountStatusesRepository.save(accountStatuses);

        // Get all the accountStatusesList
        restAccountStatusesMockMvc.perform(get("/api/account-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountStatuses.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getAccountStatuses() throws Exception {
        // Initialize the database
        accountStatusesRepository.save(accountStatuses);

        // Get the accountStatuses
        restAccountStatusesMockMvc.perform(get("/api/account-statuses/{id}", accountStatuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountStatuses.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    public void getNonExistingAccountStatuses() throws Exception {
        // Get the accountStatuses
        restAccountStatusesMockMvc.perform(get("/api/account-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAccountStatuses() throws Exception {
        // Initialize the database
        accountStatusesRepository.save(accountStatuses);

        int databaseSizeBeforeUpdate = accountStatusesRepository.findAll().size();

        // Update the accountStatuses
        AccountStatuses updatedAccountStatuses = accountStatusesRepository.findById(accountStatuses.getId()).get();
        updatedAccountStatuses
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        AccountStatusesDTO accountStatusesDTO = accountStatusesMapper.toDto(updatedAccountStatuses);

        restAccountStatusesMockMvc.perform(put("/api/account-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountStatusesDTO)))
            .andExpect(status().isOk());

        // Validate the AccountStatuses in the database
        List<AccountStatuses> accountStatusesList = accountStatusesRepository.findAll();
        assertThat(accountStatusesList).hasSize(databaseSizeBeforeUpdate);
        AccountStatuses testAccountStatuses = accountStatusesList.get(accountStatusesList.size() - 1);
        assertThat(testAccountStatuses.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAccountStatuses.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAccountStatuses.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the AccountStatuses in Elasticsearch
        verify(mockAccountStatusesSearchRepository, times(1)).save(testAccountStatuses);
    }

    @Test
    public void updateNonExistingAccountStatuses() throws Exception {
        int databaseSizeBeforeUpdate = accountStatusesRepository.findAll().size();

        // Create the AccountStatuses
        AccountStatusesDTO accountStatusesDTO = accountStatusesMapper.toDto(accountStatuses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountStatusesMockMvc.perform(put("/api/account-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountStatusesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountStatuses in the database
        List<AccountStatuses> accountStatusesList = accountStatusesRepository.findAll();
        assertThat(accountStatusesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccountStatuses in Elasticsearch
        verify(mockAccountStatusesSearchRepository, times(0)).save(accountStatuses);
    }

    @Test
    public void deleteAccountStatuses() throws Exception {
        // Initialize the database
        accountStatusesRepository.save(accountStatuses);

        int databaseSizeBeforeDelete = accountStatusesRepository.findAll().size();

        // Delete the accountStatuses
        restAccountStatusesMockMvc.perform(delete("/api/account-statuses/{id}", accountStatuses.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountStatuses> accountStatusesList = accountStatusesRepository.findAll();
        assertThat(accountStatusesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AccountStatuses in Elasticsearch
        verify(mockAccountStatusesSearchRepository, times(1)).deleteById(accountStatuses.getId());
    }

    @Test
    public void searchAccountStatuses() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        accountStatusesRepository.save(accountStatuses);
        when(mockAccountStatusesSearchRepository.search(queryStringQuery("id:" + accountStatuses.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(accountStatuses), PageRequest.of(0, 1), 1));

        // Search the accountStatuses
        restAccountStatusesMockMvc.perform(get("/api/_search/account-statuses?query=id:" + accountStatuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountStatuses.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
