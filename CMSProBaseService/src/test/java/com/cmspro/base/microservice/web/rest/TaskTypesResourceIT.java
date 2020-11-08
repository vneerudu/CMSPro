package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.TaskTypes;
import com.cmspro.base.microservice.repository.TaskTypesRepository;
import com.cmspro.base.microservice.repository.search.TaskTypesSearchRepository;
import com.cmspro.base.microservice.service.TaskTypesService;
import com.cmspro.base.microservice.service.dto.TaskTypesDTO;
import com.cmspro.base.microservice.service.mapper.TaskTypesMapper;

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
 * Integration tests for the {@link TaskTypesResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskTypesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private TaskTypesRepository taskTypesRepository;

    @Autowired
    private TaskTypesMapper taskTypesMapper;

    @Autowired
    private TaskTypesService taskTypesService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.TaskTypesSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaskTypesSearchRepository mockTaskTypesSearchRepository;

    @Autowired
    private MockMvc restTaskTypesMockMvc;

    private TaskTypes taskTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskTypes createEntity() {
        TaskTypes taskTypes = new TaskTypes()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return taskTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskTypes createUpdatedEntity() {
        TaskTypes taskTypes = new TaskTypes()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return taskTypes;
    }

    @BeforeEach
    public void initTest() {
        taskTypesRepository.deleteAll();
        taskTypes = createEntity();
    }

    @Test
    public void createTaskTypes() throws Exception {
        int databaseSizeBeforeCreate = taskTypesRepository.findAll().size();
        // Create the TaskTypes
        TaskTypesDTO taskTypesDTO = taskTypesMapper.toDto(taskTypes);
        restTaskTypesMockMvc.perform(post("/api/task-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskTypes in the database
        List<TaskTypes> taskTypesList = taskTypesRepository.findAll();
        assertThat(taskTypesList).hasSize(databaseSizeBeforeCreate + 1);
        TaskTypes testTaskTypes = taskTypesList.get(taskTypesList.size() - 1);
        assertThat(testTaskTypes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTaskTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaskTypes.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the TaskTypes in Elasticsearch
        verify(mockTaskTypesSearchRepository, times(1)).save(testTaskTypes);
    }

    @Test
    public void createTaskTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskTypesRepository.findAll().size();

        // Create the TaskTypes with an existing ID
        taskTypes.setId("existing_id");
        TaskTypesDTO taskTypesDTO = taskTypesMapper.toDto(taskTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskTypesMockMvc.perform(post("/api/task-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskTypes in the database
        List<TaskTypes> taskTypesList = taskTypesRepository.findAll();
        assertThat(taskTypesList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaskTypes in Elasticsearch
        verify(mockTaskTypesSearchRepository, times(0)).save(taskTypes);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskTypesRepository.findAll().size();
        // set the field null
        taskTypes.setCode(null);

        // Create the TaskTypes, which fails.
        TaskTypesDTO taskTypesDTO = taskTypesMapper.toDto(taskTypes);


        restTaskTypesMockMvc.perform(post("/api/task-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskTypesDTO)))
            .andExpect(status().isBadRequest());

        List<TaskTypes> taskTypesList = taskTypesRepository.findAll();
        assertThat(taskTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskTypesRepository.findAll().size();
        // set the field null
        taskTypes.setDescription(null);

        // Create the TaskTypes, which fails.
        TaskTypesDTO taskTypesDTO = taskTypesMapper.toDto(taskTypes);


        restTaskTypesMockMvc.perform(post("/api/task-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskTypesDTO)))
            .andExpect(status().isBadRequest());

        List<TaskTypes> taskTypesList = taskTypesRepository.findAll();
        assertThat(taskTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTaskTypes() throws Exception {
        // Initialize the database
        taskTypesRepository.save(taskTypes);

        // Get all the taskTypesList
        restTaskTypesMockMvc.perform(get("/api/task-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskTypes.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getTaskTypes() throws Exception {
        // Initialize the database
        taskTypesRepository.save(taskTypes);

        // Get the taskTypes
        restTaskTypesMockMvc.perform(get("/api/task-types/{id}", taskTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskTypes.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    public void getNonExistingTaskTypes() throws Exception {
        // Get the taskTypes
        restTaskTypesMockMvc.perform(get("/api/task-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTaskTypes() throws Exception {
        // Initialize the database
        taskTypesRepository.save(taskTypes);

        int databaseSizeBeforeUpdate = taskTypesRepository.findAll().size();

        // Update the taskTypes
        TaskTypes updatedTaskTypes = taskTypesRepository.findById(taskTypes.getId()).get();
        updatedTaskTypes
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        TaskTypesDTO taskTypesDTO = taskTypesMapper.toDto(updatedTaskTypes);

        restTaskTypesMockMvc.perform(put("/api/task-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskTypesDTO)))
            .andExpect(status().isOk());

        // Validate the TaskTypes in the database
        List<TaskTypes> taskTypesList = taskTypesRepository.findAll();
        assertThat(taskTypesList).hasSize(databaseSizeBeforeUpdate);
        TaskTypes testTaskTypes = taskTypesList.get(taskTypesList.size() - 1);
        assertThat(testTaskTypes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTaskTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaskTypes.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the TaskTypes in Elasticsearch
        verify(mockTaskTypesSearchRepository, times(1)).save(testTaskTypes);
    }

    @Test
    public void updateNonExistingTaskTypes() throws Exception {
        int databaseSizeBeforeUpdate = taskTypesRepository.findAll().size();

        // Create the TaskTypes
        TaskTypesDTO taskTypesDTO = taskTypesMapper.toDto(taskTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskTypesMockMvc.perform(put("/api/task-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskTypes in the database
        List<TaskTypes> taskTypesList = taskTypesRepository.findAll();
        assertThat(taskTypesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaskTypes in Elasticsearch
        verify(mockTaskTypesSearchRepository, times(0)).save(taskTypes);
    }

    @Test
    public void deleteTaskTypes() throws Exception {
        // Initialize the database
        taskTypesRepository.save(taskTypes);

        int databaseSizeBeforeDelete = taskTypesRepository.findAll().size();

        // Delete the taskTypes
        restTaskTypesMockMvc.perform(delete("/api/task-types/{id}", taskTypes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskTypes> taskTypesList = taskTypesRepository.findAll();
        assertThat(taskTypesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaskTypes in Elasticsearch
        verify(mockTaskTypesSearchRepository, times(1)).deleteById(taskTypes.getId());
    }

    @Test
    public void searchTaskTypes() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        taskTypesRepository.save(taskTypes);
        when(mockTaskTypesSearchRepository.search(queryStringQuery("id:" + taskTypes.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taskTypes), PageRequest.of(0, 1), 1));

        // Search the taskTypes
        restTaskTypesMockMvc.perform(get("/api/_search/task-types?query=id:" + taskTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskTypes.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
