package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.RootCauses;
import com.cmspro.base.microservice.repository.RootCausesRepository;
import com.cmspro.base.microservice.repository.search.RootCausesSearchRepository;
import com.cmspro.base.microservice.service.RootCausesService;
import com.cmspro.base.microservice.service.dto.RootCausesDTO;
import com.cmspro.base.microservice.service.mapper.RootCausesMapper;

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
 * Integration tests for the {@link RootCausesResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RootCausesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private RootCausesRepository rootCausesRepository;

    @Autowired
    private RootCausesMapper rootCausesMapper;

    @Autowired
    private RootCausesService rootCausesService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.RootCausesSearchRepositoryMockConfiguration
     */
    @Autowired
    private RootCausesSearchRepository mockRootCausesSearchRepository;

    @Autowired
    private MockMvc restRootCausesMockMvc;

    private RootCauses rootCauses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RootCauses createEntity() {
        RootCauses rootCauses = new RootCauses()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return rootCauses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RootCauses createUpdatedEntity() {
        RootCauses rootCauses = new RootCauses()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return rootCauses;
    }

    @BeforeEach
    public void initTest() {
        rootCausesRepository.deleteAll();
        rootCauses = createEntity();
    }

    @Test
    public void createRootCauses() throws Exception {
        int databaseSizeBeforeCreate = rootCausesRepository.findAll().size();
        // Create the RootCauses
        RootCausesDTO rootCausesDTO = rootCausesMapper.toDto(rootCauses);
        restRootCausesMockMvc.perform(post("/api/root-causes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rootCausesDTO)))
            .andExpect(status().isCreated());

        // Validate the RootCauses in the database
        List<RootCauses> rootCausesList = rootCausesRepository.findAll();
        assertThat(rootCausesList).hasSize(databaseSizeBeforeCreate + 1);
        RootCauses testRootCauses = rootCausesList.get(rootCausesList.size() - 1);
        assertThat(testRootCauses.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRootCauses.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRootCauses.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the RootCauses in Elasticsearch
        verify(mockRootCausesSearchRepository, times(1)).save(testRootCauses);
    }

    @Test
    public void createRootCausesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rootCausesRepository.findAll().size();

        // Create the RootCauses with an existing ID
        rootCauses.setId("existing_id");
        RootCausesDTO rootCausesDTO = rootCausesMapper.toDto(rootCauses);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRootCausesMockMvc.perform(post("/api/root-causes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rootCausesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RootCauses in the database
        List<RootCauses> rootCausesList = rootCausesRepository.findAll();
        assertThat(rootCausesList).hasSize(databaseSizeBeforeCreate);

        // Validate the RootCauses in Elasticsearch
        verify(mockRootCausesSearchRepository, times(0)).save(rootCauses);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rootCausesRepository.findAll().size();
        // set the field null
        rootCauses.setCode(null);

        // Create the RootCauses, which fails.
        RootCausesDTO rootCausesDTO = rootCausesMapper.toDto(rootCauses);


        restRootCausesMockMvc.perform(post("/api/root-causes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rootCausesDTO)))
            .andExpect(status().isBadRequest());

        List<RootCauses> rootCausesList = rootCausesRepository.findAll();
        assertThat(rootCausesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = rootCausesRepository.findAll().size();
        // set the field null
        rootCauses.setDescription(null);

        // Create the RootCauses, which fails.
        RootCausesDTO rootCausesDTO = rootCausesMapper.toDto(rootCauses);


        restRootCausesMockMvc.perform(post("/api/root-causes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rootCausesDTO)))
            .andExpect(status().isBadRequest());

        List<RootCauses> rootCausesList = rootCausesRepository.findAll();
        assertThat(rootCausesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRootCauses() throws Exception {
        // Initialize the database
        rootCausesRepository.save(rootCauses);

        // Get all the rootCausesList
        restRootCausesMockMvc.perform(get("/api/root-causes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rootCauses.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getRootCauses() throws Exception {
        // Initialize the database
        rootCausesRepository.save(rootCauses);

        // Get the rootCauses
        restRootCausesMockMvc.perform(get("/api/root-causes/{id}", rootCauses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rootCauses.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    public void getNonExistingRootCauses() throws Exception {
        // Get the rootCauses
        restRootCausesMockMvc.perform(get("/api/root-causes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRootCauses() throws Exception {
        // Initialize the database
        rootCausesRepository.save(rootCauses);

        int databaseSizeBeforeUpdate = rootCausesRepository.findAll().size();

        // Update the rootCauses
        RootCauses updatedRootCauses = rootCausesRepository.findById(rootCauses.getId()).get();
        updatedRootCauses
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        RootCausesDTO rootCausesDTO = rootCausesMapper.toDto(updatedRootCauses);

        restRootCausesMockMvc.perform(put("/api/root-causes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rootCausesDTO)))
            .andExpect(status().isOk());

        // Validate the RootCauses in the database
        List<RootCauses> rootCausesList = rootCausesRepository.findAll();
        assertThat(rootCausesList).hasSize(databaseSizeBeforeUpdate);
        RootCauses testRootCauses = rootCausesList.get(rootCausesList.size() - 1);
        assertThat(testRootCauses.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRootCauses.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRootCauses.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the RootCauses in Elasticsearch
        verify(mockRootCausesSearchRepository, times(1)).save(testRootCauses);
    }

    @Test
    public void updateNonExistingRootCauses() throws Exception {
        int databaseSizeBeforeUpdate = rootCausesRepository.findAll().size();

        // Create the RootCauses
        RootCausesDTO rootCausesDTO = rootCausesMapper.toDto(rootCauses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRootCausesMockMvc.perform(put("/api/root-causes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rootCausesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RootCauses in the database
        List<RootCauses> rootCausesList = rootCausesRepository.findAll();
        assertThat(rootCausesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RootCauses in Elasticsearch
        verify(mockRootCausesSearchRepository, times(0)).save(rootCauses);
    }

    @Test
    public void deleteRootCauses() throws Exception {
        // Initialize the database
        rootCausesRepository.save(rootCauses);

        int databaseSizeBeforeDelete = rootCausesRepository.findAll().size();

        // Delete the rootCauses
        restRootCausesMockMvc.perform(delete("/api/root-causes/{id}", rootCauses.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RootCauses> rootCausesList = rootCausesRepository.findAll();
        assertThat(rootCausesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RootCauses in Elasticsearch
        verify(mockRootCausesSearchRepository, times(1)).deleteById(rootCauses.getId());
    }

    @Test
    public void searchRootCauses() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        rootCausesRepository.save(rootCauses);
        when(mockRootCausesSearchRepository.search(queryStringQuery("id:" + rootCauses.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(rootCauses), PageRequest.of(0, 1), 1));

        // Search the rootCauses
        restRootCausesMockMvc.perform(get("/api/_search/root-causes?query=id:" + rootCauses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rootCauses.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
