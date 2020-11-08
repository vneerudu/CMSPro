package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.TaskLocations;
import com.cmspro.base.microservice.repository.TaskLocationsRepository;
import com.cmspro.base.microservice.repository.search.TaskLocationsSearchRepository;
import com.cmspro.base.microservice.service.TaskLocationsService;
import com.cmspro.base.microservice.service.dto.TaskLocationsDTO;
import com.cmspro.base.microservice.service.mapper.TaskLocationsMapper;

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
 * Integration tests for the {@link TaskLocationsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskLocationsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private TaskLocationsRepository taskLocationsRepository;

    @Autowired
    private TaskLocationsMapper taskLocationsMapper;

    @Autowired
    private TaskLocationsService taskLocationsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.TaskLocationsSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaskLocationsSearchRepository mockTaskLocationsSearchRepository;

    @Autowired
    private MockMvc restTaskLocationsMockMvc;

    private TaskLocations taskLocations;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskLocations createEntity() {
        TaskLocations taskLocations = new TaskLocations()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return taskLocations;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskLocations createUpdatedEntity() {
        TaskLocations taskLocations = new TaskLocations()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return taskLocations;
    }

    @BeforeEach
    public void initTest() {
        taskLocationsRepository.deleteAll();
        taskLocations = createEntity();
    }

    @Test
    public void createTaskLocations() throws Exception {
        int databaseSizeBeforeCreate = taskLocationsRepository.findAll().size();
        // Create the TaskLocations
        TaskLocationsDTO taskLocationsDTO = taskLocationsMapper.toDto(taskLocations);
        restTaskLocationsMockMvc.perform(post("/api/task-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskLocationsDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskLocations in the database
        List<TaskLocations> taskLocationsList = taskLocationsRepository.findAll();
        assertThat(taskLocationsList).hasSize(databaseSizeBeforeCreate + 1);
        TaskLocations testTaskLocations = taskLocationsList.get(taskLocationsList.size() - 1);
        assertThat(testTaskLocations.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTaskLocations.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaskLocations.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the TaskLocations in Elasticsearch
        verify(mockTaskLocationsSearchRepository, times(1)).save(testTaskLocations);
    }

    @Test
    public void createTaskLocationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskLocationsRepository.findAll().size();

        // Create the TaskLocations with an existing ID
        taskLocations.setId("existing_id");
        TaskLocationsDTO taskLocationsDTO = taskLocationsMapper.toDto(taskLocations);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskLocationsMockMvc.perform(post("/api/task-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskLocationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskLocations in the database
        List<TaskLocations> taskLocationsList = taskLocationsRepository.findAll();
        assertThat(taskLocationsList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaskLocations in Elasticsearch
        verify(mockTaskLocationsSearchRepository, times(0)).save(taskLocations);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskLocationsRepository.findAll().size();
        // set the field null
        taskLocations.setCode(null);

        // Create the TaskLocations, which fails.
        TaskLocationsDTO taskLocationsDTO = taskLocationsMapper.toDto(taskLocations);


        restTaskLocationsMockMvc.perform(post("/api/task-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskLocationsDTO)))
            .andExpect(status().isBadRequest());

        List<TaskLocations> taskLocationsList = taskLocationsRepository.findAll();
        assertThat(taskLocationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskLocationsRepository.findAll().size();
        // set the field null
        taskLocations.setDescription(null);

        // Create the TaskLocations, which fails.
        TaskLocationsDTO taskLocationsDTO = taskLocationsMapper.toDto(taskLocations);


        restTaskLocationsMockMvc.perform(post("/api/task-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskLocationsDTO)))
            .andExpect(status().isBadRequest());

        List<TaskLocations> taskLocationsList = taskLocationsRepository.findAll();
        assertThat(taskLocationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTaskLocations() throws Exception {
        // Initialize the database
        taskLocationsRepository.save(taskLocations);

        // Get all the taskLocationsList
        restTaskLocationsMockMvc.perform(get("/api/task-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskLocations.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getTaskLocations() throws Exception {
        // Initialize the database
        taskLocationsRepository.save(taskLocations);

        // Get the taskLocations
        restTaskLocationsMockMvc.perform(get("/api/task-locations/{id}", taskLocations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskLocations.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    public void getNonExistingTaskLocations() throws Exception {
        // Get the taskLocations
        restTaskLocationsMockMvc.perform(get("/api/task-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTaskLocations() throws Exception {
        // Initialize the database
        taskLocationsRepository.save(taskLocations);

        int databaseSizeBeforeUpdate = taskLocationsRepository.findAll().size();

        // Update the taskLocations
        TaskLocations updatedTaskLocations = taskLocationsRepository.findById(taskLocations.getId()).get();
        updatedTaskLocations
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        TaskLocationsDTO taskLocationsDTO = taskLocationsMapper.toDto(updatedTaskLocations);

        restTaskLocationsMockMvc.perform(put("/api/task-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskLocationsDTO)))
            .andExpect(status().isOk());

        // Validate the TaskLocations in the database
        List<TaskLocations> taskLocationsList = taskLocationsRepository.findAll();
        assertThat(taskLocationsList).hasSize(databaseSizeBeforeUpdate);
        TaskLocations testTaskLocations = taskLocationsList.get(taskLocationsList.size() - 1);
        assertThat(testTaskLocations.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTaskLocations.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaskLocations.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the TaskLocations in Elasticsearch
        verify(mockTaskLocationsSearchRepository, times(1)).save(testTaskLocations);
    }

    @Test
    public void updateNonExistingTaskLocations() throws Exception {
        int databaseSizeBeforeUpdate = taskLocationsRepository.findAll().size();

        // Create the TaskLocations
        TaskLocationsDTO taskLocationsDTO = taskLocationsMapper.toDto(taskLocations);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskLocationsMockMvc.perform(put("/api/task-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskLocationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskLocations in the database
        List<TaskLocations> taskLocationsList = taskLocationsRepository.findAll();
        assertThat(taskLocationsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaskLocations in Elasticsearch
        verify(mockTaskLocationsSearchRepository, times(0)).save(taskLocations);
    }

    @Test
    public void deleteTaskLocations() throws Exception {
        // Initialize the database
        taskLocationsRepository.save(taskLocations);

        int databaseSizeBeforeDelete = taskLocationsRepository.findAll().size();

        // Delete the taskLocations
        restTaskLocationsMockMvc.perform(delete("/api/task-locations/{id}", taskLocations.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskLocations> taskLocationsList = taskLocationsRepository.findAll();
        assertThat(taskLocationsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaskLocations in Elasticsearch
        verify(mockTaskLocationsSearchRepository, times(1)).deleteById(taskLocations.getId());
    }

    @Test
    public void searchTaskLocations() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        taskLocationsRepository.save(taskLocations);
        when(mockTaskLocationsSearchRepository.search(queryStringQuery("id:" + taskLocations.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taskLocations), PageRequest.of(0, 1), 1));

        // Search the taskLocations
        restTaskLocationsMockMvc.perform(get("/api/_search/task-locations?query=id:" + taskLocations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskLocations.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
