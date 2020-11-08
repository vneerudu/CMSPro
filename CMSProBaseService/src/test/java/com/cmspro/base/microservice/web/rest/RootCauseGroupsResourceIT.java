package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.RootCauseGroups;
import com.cmspro.base.microservice.repository.RootCauseGroupsRepository;
import com.cmspro.base.microservice.repository.search.RootCauseGroupsSearchRepository;
import com.cmspro.base.microservice.service.RootCauseGroupsService;
import com.cmspro.base.microservice.service.dto.RootCauseGroupsDTO;
import com.cmspro.base.microservice.service.mapper.RootCauseGroupsMapper;

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
 * Integration tests for the {@link RootCauseGroupsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RootCauseGroupsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private RootCauseGroupsRepository rootCauseGroupsRepository;

    @Autowired
    private RootCauseGroupsMapper rootCauseGroupsMapper;

    @Autowired
    private RootCauseGroupsService rootCauseGroupsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.RootCauseGroupsSearchRepositoryMockConfiguration
     */
    @Autowired
    private RootCauseGroupsSearchRepository mockRootCauseGroupsSearchRepository;

    @Autowired
    private MockMvc restRootCauseGroupsMockMvc;

    private RootCauseGroups rootCauseGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RootCauseGroups createEntity() {
        RootCauseGroups rootCauseGroups = new RootCauseGroups()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return rootCauseGroups;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RootCauseGroups createUpdatedEntity() {
        RootCauseGroups rootCauseGroups = new RootCauseGroups()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return rootCauseGroups;
    }

    @BeforeEach
    public void initTest() {
        rootCauseGroupsRepository.deleteAll();
        rootCauseGroups = createEntity();
    }

    @Test
    public void createRootCauseGroups() throws Exception {
        int databaseSizeBeforeCreate = rootCauseGroupsRepository.findAll().size();
        // Create the RootCauseGroups
        RootCauseGroupsDTO rootCauseGroupsDTO = rootCauseGroupsMapper.toDto(rootCauseGroups);
        restRootCauseGroupsMockMvc.perform(post("/api/root-cause-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rootCauseGroupsDTO)))
            .andExpect(status().isCreated());

        // Validate the RootCauseGroups in the database
        List<RootCauseGroups> rootCauseGroupsList = rootCauseGroupsRepository.findAll();
        assertThat(rootCauseGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        RootCauseGroups testRootCauseGroups = rootCauseGroupsList.get(rootCauseGroupsList.size() - 1);
        assertThat(testRootCauseGroups.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRootCauseGroups.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRootCauseGroups.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the RootCauseGroups in Elasticsearch
        verify(mockRootCauseGroupsSearchRepository, times(1)).save(testRootCauseGroups);
    }

    @Test
    public void createRootCauseGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rootCauseGroupsRepository.findAll().size();

        // Create the RootCauseGroups with an existing ID
        rootCauseGroups.setId("existing_id");
        RootCauseGroupsDTO rootCauseGroupsDTO = rootCauseGroupsMapper.toDto(rootCauseGroups);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRootCauseGroupsMockMvc.perform(post("/api/root-cause-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rootCauseGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RootCauseGroups in the database
        List<RootCauseGroups> rootCauseGroupsList = rootCauseGroupsRepository.findAll();
        assertThat(rootCauseGroupsList).hasSize(databaseSizeBeforeCreate);

        // Validate the RootCauseGroups in Elasticsearch
        verify(mockRootCauseGroupsSearchRepository, times(0)).save(rootCauseGroups);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rootCauseGroupsRepository.findAll().size();
        // set the field null
        rootCauseGroups.setCode(null);

        // Create the RootCauseGroups, which fails.
        RootCauseGroupsDTO rootCauseGroupsDTO = rootCauseGroupsMapper.toDto(rootCauseGroups);


        restRootCauseGroupsMockMvc.perform(post("/api/root-cause-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rootCauseGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<RootCauseGroups> rootCauseGroupsList = rootCauseGroupsRepository.findAll();
        assertThat(rootCauseGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = rootCauseGroupsRepository.findAll().size();
        // set the field null
        rootCauseGroups.setDescription(null);

        // Create the RootCauseGroups, which fails.
        RootCauseGroupsDTO rootCauseGroupsDTO = rootCauseGroupsMapper.toDto(rootCauseGroups);


        restRootCauseGroupsMockMvc.perform(post("/api/root-cause-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rootCauseGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<RootCauseGroups> rootCauseGroupsList = rootCauseGroupsRepository.findAll();
        assertThat(rootCauseGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRootCauseGroups() throws Exception {
        // Initialize the database
        rootCauseGroupsRepository.save(rootCauseGroups);

        // Get all the rootCauseGroupsList
        restRootCauseGroupsMockMvc.perform(get("/api/root-cause-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rootCauseGroups.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getRootCauseGroups() throws Exception {
        // Initialize the database
        rootCauseGroupsRepository.save(rootCauseGroups);

        // Get the rootCauseGroups
        restRootCauseGroupsMockMvc.perform(get("/api/root-cause-groups/{id}", rootCauseGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rootCauseGroups.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    public void getNonExistingRootCauseGroups() throws Exception {
        // Get the rootCauseGroups
        restRootCauseGroupsMockMvc.perform(get("/api/root-cause-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRootCauseGroups() throws Exception {
        // Initialize the database
        rootCauseGroupsRepository.save(rootCauseGroups);

        int databaseSizeBeforeUpdate = rootCauseGroupsRepository.findAll().size();

        // Update the rootCauseGroups
        RootCauseGroups updatedRootCauseGroups = rootCauseGroupsRepository.findById(rootCauseGroups.getId()).get();
        updatedRootCauseGroups
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        RootCauseGroupsDTO rootCauseGroupsDTO = rootCauseGroupsMapper.toDto(updatedRootCauseGroups);

        restRootCauseGroupsMockMvc.perform(put("/api/root-cause-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rootCauseGroupsDTO)))
            .andExpect(status().isOk());

        // Validate the RootCauseGroups in the database
        List<RootCauseGroups> rootCauseGroupsList = rootCauseGroupsRepository.findAll();
        assertThat(rootCauseGroupsList).hasSize(databaseSizeBeforeUpdate);
        RootCauseGroups testRootCauseGroups = rootCauseGroupsList.get(rootCauseGroupsList.size() - 1);
        assertThat(testRootCauseGroups.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRootCauseGroups.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRootCauseGroups.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the RootCauseGroups in Elasticsearch
        verify(mockRootCauseGroupsSearchRepository, times(1)).save(testRootCauseGroups);
    }

    @Test
    public void updateNonExistingRootCauseGroups() throws Exception {
        int databaseSizeBeforeUpdate = rootCauseGroupsRepository.findAll().size();

        // Create the RootCauseGroups
        RootCauseGroupsDTO rootCauseGroupsDTO = rootCauseGroupsMapper.toDto(rootCauseGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRootCauseGroupsMockMvc.perform(put("/api/root-cause-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rootCauseGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RootCauseGroups in the database
        List<RootCauseGroups> rootCauseGroupsList = rootCauseGroupsRepository.findAll();
        assertThat(rootCauseGroupsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RootCauseGroups in Elasticsearch
        verify(mockRootCauseGroupsSearchRepository, times(0)).save(rootCauseGroups);
    }

    @Test
    public void deleteRootCauseGroups() throws Exception {
        // Initialize the database
        rootCauseGroupsRepository.save(rootCauseGroups);

        int databaseSizeBeforeDelete = rootCauseGroupsRepository.findAll().size();

        // Delete the rootCauseGroups
        restRootCauseGroupsMockMvc.perform(delete("/api/root-cause-groups/{id}", rootCauseGroups.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RootCauseGroups> rootCauseGroupsList = rootCauseGroupsRepository.findAll();
        assertThat(rootCauseGroupsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RootCauseGroups in Elasticsearch
        verify(mockRootCauseGroupsSearchRepository, times(1)).deleteById(rootCauseGroups.getId());
    }

    @Test
    public void searchRootCauseGroups() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        rootCauseGroupsRepository.save(rootCauseGroups);
        when(mockRootCauseGroupsSearchRepository.search(queryStringQuery("id:" + rootCauseGroups.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(rootCauseGroups), PageRequest.of(0, 1), 1));

        // Search the rootCauseGroups
        restRootCauseGroupsMockMvc.perform(get("/api/_search/root-cause-groups?query=id:" + rootCauseGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rootCauseGroups.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
