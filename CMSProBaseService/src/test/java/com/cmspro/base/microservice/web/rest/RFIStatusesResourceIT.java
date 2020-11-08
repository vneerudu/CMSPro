package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.RFIStatuses;
import com.cmspro.base.microservice.repository.RFIStatusesRepository;
import com.cmspro.base.microservice.repository.search.RFIStatusesSearchRepository;
import com.cmspro.base.microservice.service.RFIStatusesService;
import com.cmspro.base.microservice.service.dto.RFIStatusesDTO;
import com.cmspro.base.microservice.service.mapper.RFIStatusesMapper;

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
 * Integration tests for the {@link RFIStatusesResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RFIStatusesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private RFIStatusesRepository rFIStatusesRepository;

    @Autowired
    private RFIStatusesMapper rFIStatusesMapper;

    @Autowired
    private RFIStatusesService rFIStatusesService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.RFIStatusesSearchRepositoryMockConfiguration
     */
    @Autowired
    private RFIStatusesSearchRepository mockRFIStatusesSearchRepository;

    @Autowired
    private MockMvc restRFIStatusesMockMvc;

    private RFIStatuses rFIStatuses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFIStatuses createEntity() {
        RFIStatuses rFIStatuses = new RFIStatuses()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return rFIStatuses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFIStatuses createUpdatedEntity() {
        RFIStatuses rFIStatuses = new RFIStatuses()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return rFIStatuses;
    }

    @BeforeEach
    public void initTest() {
        rFIStatusesRepository.deleteAll();
        rFIStatuses = createEntity();
    }

    @Test
    public void createRFIStatuses() throws Exception {
        int databaseSizeBeforeCreate = rFIStatusesRepository.findAll().size();
        // Create the RFIStatuses
        RFIStatusesDTO rFIStatusesDTO = rFIStatusesMapper.toDto(rFIStatuses);
        restRFIStatusesMockMvc.perform(post("/api/rfi-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFIStatusesDTO)))
            .andExpect(status().isCreated());

        // Validate the RFIStatuses in the database
        List<RFIStatuses> rFIStatusesList = rFIStatusesRepository.findAll();
        assertThat(rFIStatusesList).hasSize(databaseSizeBeforeCreate + 1);
        RFIStatuses testRFIStatuses = rFIStatusesList.get(rFIStatusesList.size() - 1);
        assertThat(testRFIStatuses.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRFIStatuses.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRFIStatuses.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the RFIStatuses in Elasticsearch
        verify(mockRFIStatusesSearchRepository, times(1)).save(testRFIStatuses);
    }

    @Test
    public void createRFIStatusesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rFIStatusesRepository.findAll().size();

        // Create the RFIStatuses with an existing ID
        rFIStatuses.setId("existing_id");
        RFIStatusesDTO rFIStatusesDTO = rFIStatusesMapper.toDto(rFIStatuses);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFIStatusesMockMvc.perform(post("/api/rfi-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFIStatusesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RFIStatuses in the database
        List<RFIStatuses> rFIStatusesList = rFIStatusesRepository.findAll();
        assertThat(rFIStatusesList).hasSize(databaseSizeBeforeCreate);

        // Validate the RFIStatuses in Elasticsearch
        verify(mockRFIStatusesSearchRepository, times(0)).save(rFIStatuses);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rFIStatusesRepository.findAll().size();
        // set the field null
        rFIStatuses.setCode(null);

        // Create the RFIStatuses, which fails.
        RFIStatusesDTO rFIStatusesDTO = rFIStatusesMapper.toDto(rFIStatuses);


        restRFIStatusesMockMvc.perform(post("/api/rfi-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFIStatusesDTO)))
            .andExpect(status().isBadRequest());

        List<RFIStatuses> rFIStatusesList = rFIStatusesRepository.findAll();
        assertThat(rFIStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = rFIStatusesRepository.findAll().size();
        // set the field null
        rFIStatuses.setDescription(null);

        // Create the RFIStatuses, which fails.
        RFIStatusesDTO rFIStatusesDTO = rFIStatusesMapper.toDto(rFIStatuses);


        restRFIStatusesMockMvc.perform(post("/api/rfi-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFIStatusesDTO)))
            .andExpect(status().isBadRequest());

        List<RFIStatuses> rFIStatusesList = rFIStatusesRepository.findAll();
        assertThat(rFIStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRFIStatuses() throws Exception {
        // Initialize the database
        rFIStatusesRepository.save(rFIStatuses);

        // Get all the rFIStatusesList
        restRFIStatusesMockMvc.perform(get("/api/rfi-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFIStatuses.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getRFIStatuses() throws Exception {
        // Initialize the database
        rFIStatusesRepository.save(rFIStatuses);

        // Get the rFIStatuses
        restRFIStatusesMockMvc.perform(get("/api/rfi-statuses/{id}", rFIStatuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFIStatuses.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    public void getNonExistingRFIStatuses() throws Exception {
        // Get the rFIStatuses
        restRFIStatusesMockMvc.perform(get("/api/rfi-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRFIStatuses() throws Exception {
        // Initialize the database
        rFIStatusesRepository.save(rFIStatuses);

        int databaseSizeBeforeUpdate = rFIStatusesRepository.findAll().size();

        // Update the rFIStatuses
        RFIStatuses updatedRFIStatuses = rFIStatusesRepository.findById(rFIStatuses.getId()).get();
        updatedRFIStatuses
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        RFIStatusesDTO rFIStatusesDTO = rFIStatusesMapper.toDto(updatedRFIStatuses);

        restRFIStatusesMockMvc.perform(put("/api/rfi-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFIStatusesDTO)))
            .andExpect(status().isOk());

        // Validate the RFIStatuses in the database
        List<RFIStatuses> rFIStatusesList = rFIStatusesRepository.findAll();
        assertThat(rFIStatusesList).hasSize(databaseSizeBeforeUpdate);
        RFIStatuses testRFIStatuses = rFIStatusesList.get(rFIStatusesList.size() - 1);
        assertThat(testRFIStatuses.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRFIStatuses.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRFIStatuses.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the RFIStatuses in Elasticsearch
        verify(mockRFIStatusesSearchRepository, times(1)).save(testRFIStatuses);
    }

    @Test
    public void updateNonExistingRFIStatuses() throws Exception {
        int databaseSizeBeforeUpdate = rFIStatusesRepository.findAll().size();

        // Create the RFIStatuses
        RFIStatusesDTO rFIStatusesDTO = rFIStatusesMapper.toDto(rFIStatuses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFIStatusesMockMvc.perform(put("/api/rfi-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFIStatusesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RFIStatuses in the database
        List<RFIStatuses> rFIStatusesList = rFIStatusesRepository.findAll();
        assertThat(rFIStatusesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RFIStatuses in Elasticsearch
        verify(mockRFIStatusesSearchRepository, times(0)).save(rFIStatuses);
    }

    @Test
    public void deleteRFIStatuses() throws Exception {
        // Initialize the database
        rFIStatusesRepository.save(rFIStatuses);

        int databaseSizeBeforeDelete = rFIStatusesRepository.findAll().size();

        // Delete the rFIStatuses
        restRFIStatusesMockMvc.perform(delete("/api/rfi-statuses/{id}", rFIStatuses.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFIStatuses> rFIStatusesList = rFIStatusesRepository.findAll();
        assertThat(rFIStatusesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RFIStatuses in Elasticsearch
        verify(mockRFIStatusesSearchRepository, times(1)).deleteById(rFIStatuses.getId());
    }

    @Test
    public void searchRFIStatuses() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        rFIStatusesRepository.save(rFIStatuses);
        when(mockRFIStatusesSearchRepository.search(queryStringQuery("id:" + rFIStatuses.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(rFIStatuses), PageRequest.of(0, 1), 1));

        // Search the rFIStatuses
        restRFIStatusesMockMvc.perform(get("/api/_search/rfi-statuses?query=id:" + rFIStatuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFIStatuses.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
