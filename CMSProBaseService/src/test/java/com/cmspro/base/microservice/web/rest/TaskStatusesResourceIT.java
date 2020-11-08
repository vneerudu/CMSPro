package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.TaskStatuses;
import com.cmspro.base.microservice.repository.TaskStatusesRepository;
import com.cmspro.base.microservice.repository.search.TaskStatusesSearchRepository;
import com.cmspro.base.microservice.service.TaskStatusesService;
import com.cmspro.base.microservice.service.dto.TaskStatusesDTO;
import com.cmspro.base.microservice.service.mapper.TaskStatusesMapper;

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
 * Integration tests for the {@link TaskStatusesResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskStatusesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private TaskStatusesRepository taskStatusesRepository;

    @Autowired
    private TaskStatusesMapper taskStatusesMapper;

    @Autowired
    private TaskStatusesService taskStatusesService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.TaskStatusesSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaskStatusesSearchRepository mockTaskStatusesSearchRepository;

    @Autowired
    private MockMvc restTaskStatusesMockMvc;

    private TaskStatuses taskStatuses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskStatuses createEntity() {
        TaskStatuses taskStatuses = new TaskStatuses()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return taskStatuses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskStatuses createUpdatedEntity() {
        TaskStatuses taskStatuses = new TaskStatuses()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return taskStatuses;
    }

    @BeforeEach
    public void initTest() {
        taskStatusesRepository.deleteAll();
        taskStatuses = createEntity();
    }

    @Test
    public void createTaskStatuses() throws Exception {
        int databaseSizeBeforeCreate = taskStatusesRepository.findAll().size();
        // Create the TaskStatuses
        TaskStatusesDTO taskStatusesDTO = taskStatusesMapper.toDto(taskStatuses);
        restTaskStatusesMockMvc.perform(post("/api/task-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskStatusesDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskStatuses in the database
        List<TaskStatuses> taskStatusesList = taskStatusesRepository.findAll();
        assertThat(taskStatusesList).hasSize(databaseSizeBeforeCreate + 1);
        TaskStatuses testTaskStatuses = taskStatusesList.get(taskStatusesList.size() - 1);
        assertThat(testTaskStatuses.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTaskStatuses.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaskStatuses.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the TaskStatuses in Elasticsearch
        verify(mockTaskStatusesSearchRepository, times(1)).save(testTaskStatuses);
    }

    @Test
    public void createTaskStatusesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskStatusesRepository.findAll().size();

        // Create the TaskStatuses with an existing ID
        taskStatuses.setId("existing_id");
        TaskStatusesDTO taskStatusesDTO = taskStatusesMapper.toDto(taskStatuses);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskStatusesMockMvc.perform(post("/api/task-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskStatusesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskStatuses in the database
        List<TaskStatuses> taskStatusesList = taskStatusesRepository.findAll();
        assertThat(taskStatusesList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaskStatuses in Elasticsearch
        verify(mockTaskStatusesSearchRepository, times(0)).save(taskStatuses);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskStatusesRepository.findAll().size();
        // set the field null
        taskStatuses.setCode(null);

        // Create the TaskStatuses, which fails.
        TaskStatusesDTO taskStatusesDTO = taskStatusesMapper.toDto(taskStatuses);


        restTaskStatusesMockMvc.perform(post("/api/task-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskStatusesDTO)))
            .andExpect(status().isBadRequest());

        List<TaskStatuses> taskStatusesList = taskStatusesRepository.findAll();
        assertThat(taskStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskStatusesRepository.findAll().size();
        // set the field null
        taskStatuses.setDescription(null);

        // Create the TaskStatuses, which fails.
        TaskStatusesDTO taskStatusesDTO = taskStatusesMapper.toDto(taskStatuses);


        restTaskStatusesMockMvc.perform(post("/api/task-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskStatusesDTO)))
            .andExpect(status().isBadRequest());

        List<TaskStatuses> taskStatusesList = taskStatusesRepository.findAll();
        assertThat(taskStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTaskStatuses() throws Exception {
        // Initialize the database
        taskStatusesRepository.save(taskStatuses);

        // Get all the taskStatusesList
        restTaskStatusesMockMvc.perform(get("/api/task-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskStatuses.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getTaskStatuses() throws Exception {
        // Initialize the database
        taskStatusesRepository.save(taskStatuses);

        // Get the taskStatuses
        restTaskStatusesMockMvc.perform(get("/api/task-statuses/{id}", taskStatuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskStatuses.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    public void getNonExistingTaskStatuses() throws Exception {
        // Get the taskStatuses
        restTaskStatusesMockMvc.perform(get("/api/task-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTaskStatuses() throws Exception {
        // Initialize the database
        taskStatusesRepository.save(taskStatuses);

        int databaseSizeBeforeUpdate = taskStatusesRepository.findAll().size();

        // Update the taskStatuses
        TaskStatuses updatedTaskStatuses = taskStatusesRepository.findById(taskStatuses.getId()).get();
        updatedTaskStatuses
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        TaskStatusesDTO taskStatusesDTO = taskStatusesMapper.toDto(updatedTaskStatuses);

        restTaskStatusesMockMvc.perform(put("/api/task-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskStatusesDTO)))
            .andExpect(status().isOk());

        // Validate the TaskStatuses in the database
        List<TaskStatuses> taskStatusesList = taskStatusesRepository.findAll();
        assertThat(taskStatusesList).hasSize(databaseSizeBeforeUpdate);
        TaskStatuses testTaskStatuses = taskStatusesList.get(taskStatusesList.size() - 1);
        assertThat(testTaskStatuses.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTaskStatuses.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaskStatuses.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the TaskStatuses in Elasticsearch
        verify(mockTaskStatusesSearchRepository, times(1)).save(testTaskStatuses);
    }

    @Test
    public void updateNonExistingTaskStatuses() throws Exception {
        int databaseSizeBeforeUpdate = taskStatusesRepository.findAll().size();

        // Create the TaskStatuses
        TaskStatusesDTO taskStatusesDTO = taskStatusesMapper.toDto(taskStatuses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskStatusesMockMvc.perform(put("/api/task-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskStatusesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskStatuses in the database
        List<TaskStatuses> taskStatusesList = taskStatusesRepository.findAll();
        assertThat(taskStatusesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaskStatuses in Elasticsearch
        verify(mockTaskStatusesSearchRepository, times(0)).save(taskStatuses);
    }

    @Test
    public void deleteTaskStatuses() throws Exception {
        // Initialize the database
        taskStatusesRepository.save(taskStatuses);

        int databaseSizeBeforeDelete = taskStatusesRepository.findAll().size();

        // Delete the taskStatuses
        restTaskStatusesMockMvc.perform(delete("/api/task-statuses/{id}", taskStatuses.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskStatuses> taskStatusesList = taskStatusesRepository.findAll();
        assertThat(taskStatusesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaskStatuses in Elasticsearch
        verify(mockTaskStatusesSearchRepository, times(1)).deleteById(taskStatuses.getId());
    }

    @Test
    public void searchTaskStatuses() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        taskStatusesRepository.save(taskStatuses);
        when(mockTaskStatusesSearchRepository.search(queryStringQuery("id:" + taskStatuses.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taskStatuses), PageRequest.of(0, 1), 1));

        // Search the taskStatuses
        restTaskStatusesMockMvc.perform(get("/api/_search/task-statuses?query=id:" + taskStatuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskStatuses.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
