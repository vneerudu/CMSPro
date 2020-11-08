package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.States;
import com.cmspro.base.microservice.repository.StatesRepository;
import com.cmspro.base.microservice.repository.search.StatesSearchRepository;
import com.cmspro.base.microservice.service.StatesService;
import com.cmspro.base.microservice.service.dto.StatesDTO;
import com.cmspro.base.microservice.service.mapper.StatesMapper;

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
 * Integration tests for the {@link StatesResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class StatesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private StatesMapper statesMapper;

    @Autowired
    private StatesService statesService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.StatesSearchRepositoryMockConfiguration
     */
    @Autowired
    private StatesSearchRepository mockStatesSearchRepository;

    @Autowired
    private MockMvc restStatesMockMvc;

    private States states;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static States createEntity() {
        States states = new States()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return states;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static States createUpdatedEntity() {
        States states = new States()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return states;
    }

    @BeforeEach
    public void initTest() {
        statesRepository.deleteAll();
        states = createEntity();
    }

    @Test
    public void createStates() throws Exception {
        int databaseSizeBeforeCreate = statesRepository.findAll().size();
        // Create the States
        StatesDTO statesDTO = statesMapper.toDto(states);
        restStatesMockMvc.perform(post("/api/states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statesDTO)))
            .andExpect(status().isCreated());

        // Validate the States in the database
        List<States> statesList = statesRepository.findAll();
        assertThat(statesList).hasSize(databaseSizeBeforeCreate + 1);
        States testStates = statesList.get(statesList.size() - 1);
        assertThat(testStates.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStates.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the States in Elasticsearch
        verify(mockStatesSearchRepository, times(1)).save(testStates);
    }

    @Test
    public void createStatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statesRepository.findAll().size();

        // Create the States with an existing ID
        states.setId("existing_id");
        StatesDTO statesDTO = statesMapper.toDto(states);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatesMockMvc.perform(post("/api/states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the States in the database
        List<States> statesList = statesRepository.findAll();
        assertThat(statesList).hasSize(databaseSizeBeforeCreate);

        // Validate the States in Elasticsearch
        verify(mockStatesSearchRepository, times(0)).save(states);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = statesRepository.findAll().size();
        // set the field null
        states.setCode(null);

        // Create the States, which fails.
        StatesDTO statesDTO = statesMapper.toDto(states);


        restStatesMockMvc.perform(post("/api/states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statesDTO)))
            .andExpect(status().isBadRequest());

        List<States> statesList = statesRepository.findAll();
        assertThat(statesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = statesRepository.findAll().size();
        // set the field null
        states.setDescription(null);

        // Create the States, which fails.
        StatesDTO statesDTO = statesMapper.toDto(states);


        restStatesMockMvc.perform(post("/api/states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statesDTO)))
            .andExpect(status().isBadRequest());

        List<States> statesList = statesRepository.findAll();
        assertThat(statesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllStates() throws Exception {
        // Initialize the database
        statesRepository.save(states);

        // Get all the statesList
        restStatesMockMvc.perform(get("/api/states?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(states.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    public void getStates() throws Exception {
        // Initialize the database
        statesRepository.save(states);

        // Get the states
        restStatesMockMvc.perform(get("/api/states/{id}", states.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(states.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    public void getNonExistingStates() throws Exception {
        // Get the states
        restStatesMockMvc.perform(get("/api/states/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateStates() throws Exception {
        // Initialize the database
        statesRepository.save(states);

        int databaseSizeBeforeUpdate = statesRepository.findAll().size();

        // Update the states
        States updatedStates = statesRepository.findById(states.getId()).get();
        updatedStates
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        StatesDTO statesDTO = statesMapper.toDto(updatedStates);

        restStatesMockMvc.perform(put("/api/states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statesDTO)))
            .andExpect(status().isOk());

        // Validate the States in the database
        List<States> statesList = statesRepository.findAll();
        assertThat(statesList).hasSize(databaseSizeBeforeUpdate);
        States testStates = statesList.get(statesList.size() - 1);
        assertThat(testStates.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStates.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the States in Elasticsearch
        verify(mockStatesSearchRepository, times(1)).save(testStates);
    }

    @Test
    public void updateNonExistingStates() throws Exception {
        int databaseSizeBeforeUpdate = statesRepository.findAll().size();

        // Create the States
        StatesDTO statesDTO = statesMapper.toDto(states);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatesMockMvc.perform(put("/api/states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the States in the database
        List<States> statesList = statesRepository.findAll();
        assertThat(statesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the States in Elasticsearch
        verify(mockStatesSearchRepository, times(0)).save(states);
    }

    @Test
    public void deleteStates() throws Exception {
        // Initialize the database
        statesRepository.save(states);

        int databaseSizeBeforeDelete = statesRepository.findAll().size();

        // Delete the states
        restStatesMockMvc.perform(delete("/api/states/{id}", states.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<States> statesList = statesRepository.findAll();
        assertThat(statesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the States in Elasticsearch
        verify(mockStatesSearchRepository, times(1)).deleteById(states.getId());
    }

    @Test
    public void searchStates() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        statesRepository.save(states);
        when(mockStatesSearchRepository.search(queryStringQuery("id:" + states.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(states), PageRequest.of(0, 1), 1));

        // Search the states
        restStatesMockMvc.perform(get("/api/_search/states?query=id:" + states.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(states.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
