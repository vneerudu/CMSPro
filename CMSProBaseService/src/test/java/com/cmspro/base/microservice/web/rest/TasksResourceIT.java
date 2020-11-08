package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.Tasks;
import com.cmspro.base.microservice.repository.TasksRepository;
import com.cmspro.base.microservice.repository.search.TasksSearchRepository;
import com.cmspro.base.microservice.service.TasksService;
import com.cmspro.base.microservice.service.dto.TasksDTO;
import com.cmspro.base.microservice.service.mapper.TasksMapper;

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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TasksResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TasksResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COST_IMPACT = false;
    private static final Boolean UPDATED_COST_IMPACT = true;

    private static final String DEFAULT_COST_IMPACT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COST_IMPACT_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SCHEDULE_IMPACT = false;
    private static final Boolean UPDATED_SCHEDULE_IMPACT = true;

    private static final String DEFAULT_SCHEDULE_IMPACT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_SCHEDULE_IMPACT_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private TasksMapper tasksMapper;

    @Autowired
    private TasksService tasksService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.TasksSearchRepositoryMockConfiguration
     */
    @Autowired
    private TasksSearchRepository mockTasksSearchRepository;

    @Autowired
    private MockMvc restTasksMockMvc;

    private Tasks tasks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tasks createEntity() {
        Tasks tasks = new Tasks()
            .title(DEFAULT_TITLE)
            .startDate(DEFAULT_START_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .description(DEFAULT_DESCRIPTION)
            .costImpact(DEFAULT_COST_IMPACT)
            .costImpactComment(DEFAULT_COST_IMPACT_COMMENT)
            .scheduleImpact(DEFAULT_SCHEDULE_IMPACT)
            .scheduleImpactComment(DEFAULT_SCHEDULE_IMPACT_COMMENT)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return tasks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tasks createUpdatedEntity() {
        Tasks tasks = new Tasks()
            .title(UPDATED_TITLE)
            .startDate(UPDATED_START_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .description(UPDATED_DESCRIPTION)
            .costImpact(UPDATED_COST_IMPACT)
            .costImpactComment(UPDATED_COST_IMPACT_COMMENT)
            .scheduleImpact(UPDATED_SCHEDULE_IMPACT)
            .scheduleImpactComment(UPDATED_SCHEDULE_IMPACT_COMMENT)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return tasks;
    }

    @BeforeEach
    public void initTest() {
        tasksRepository.deleteAll();
        tasks = createEntity();
    }

    @Test
    public void createTasks() throws Exception {
        int databaseSizeBeforeCreate = tasksRepository.findAll().size();
        // Create the Tasks
        TasksDTO tasksDTO = tasksMapper.toDto(tasks);
        restTasksMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isCreated());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeCreate + 1);
        Tasks testTasks = tasksList.get(tasksList.size() - 1);
        assertThat(testTasks.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTasks.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTasks.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testTasks.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTasks.isCostImpact()).isEqualTo(DEFAULT_COST_IMPACT);
        assertThat(testTasks.getCostImpactComment()).isEqualTo(DEFAULT_COST_IMPACT_COMMENT);
        assertThat(testTasks.isScheduleImpact()).isEqualTo(DEFAULT_SCHEDULE_IMPACT);
        assertThat(testTasks.getScheduleImpactComment()).isEqualTo(DEFAULT_SCHEDULE_IMPACT_COMMENT);
        assertThat(testTasks.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTasks.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the Tasks in Elasticsearch
        verify(mockTasksSearchRepository, times(1)).save(testTasks);
    }

    @Test
    public void createTasksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tasksRepository.findAll().size();

        // Create the Tasks with an existing ID
        tasks.setId("existing_id");
        TasksDTO tasksDTO = tasksMapper.toDto(tasks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTasksMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeCreate);

        // Validate the Tasks in Elasticsearch
        verify(mockTasksSearchRepository, times(0)).save(tasks);
    }


    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = tasksRepository.findAll().size();
        // set the field null
        tasks.setTitle(null);

        // Create the Tasks, which fails.
        TasksDTO tasksDTO = tasksMapper.toDto(tasks);


        restTasksMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isBadRequest());

        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = tasksRepository.findAll().size();
        // set the field null
        tasks.setCreatedBy(null);

        // Create the Tasks, which fails.
        TasksDTO tasksDTO = tasksMapper.toDto(tasks);


        restTasksMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isBadRequest());

        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tasksRepository.findAll().size();
        // set the field null
        tasks.setCreationDate(null);

        // Create the Tasks, which fails.
        TasksDTO tasksDTO = tasksMapper.toDto(tasks);


        restTasksMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isBadRequest());

        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTasks() throws Exception {
        // Initialize the database
        tasksRepository.save(tasks);

        // Get all the tasksList
        restTasksMockMvc.perform(get("/api/tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tasks.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].costImpact").value(hasItem(DEFAULT_COST_IMPACT.booleanValue())))
            .andExpect(jsonPath("$.[*].costImpactComment").value(hasItem(DEFAULT_COST_IMPACT_COMMENT)))
            .andExpect(jsonPath("$.[*].scheduleImpact").value(hasItem(DEFAULT_SCHEDULE_IMPACT.booleanValue())))
            .andExpect(jsonPath("$.[*].scheduleImpactComment").value(hasItem(DEFAULT_SCHEDULE_IMPACT_COMMENT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getTasks() throws Exception {
        // Initialize the database
        tasksRepository.save(tasks);

        // Get the tasks
        restTasksMockMvc.perform(get("/api/tasks/{id}", tasks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tasks.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.costImpact").value(DEFAULT_COST_IMPACT.booleanValue()))
            .andExpect(jsonPath("$.costImpactComment").value(DEFAULT_COST_IMPACT_COMMENT))
            .andExpect(jsonPath("$.scheduleImpact").value(DEFAULT_SCHEDULE_IMPACT.booleanValue()))
            .andExpect(jsonPath("$.scheduleImpactComment").value(DEFAULT_SCHEDULE_IMPACT_COMMENT))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingTasks() throws Exception {
        // Get the tasks
        restTasksMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTasks() throws Exception {
        // Initialize the database
        tasksRepository.save(tasks);

        int databaseSizeBeforeUpdate = tasksRepository.findAll().size();

        // Update the tasks
        Tasks updatedTasks = tasksRepository.findById(tasks.getId()).get();
        updatedTasks
            .title(UPDATED_TITLE)
            .startDate(UPDATED_START_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .description(UPDATED_DESCRIPTION)
            .costImpact(UPDATED_COST_IMPACT)
            .costImpactComment(UPDATED_COST_IMPACT_COMMENT)
            .scheduleImpact(UPDATED_SCHEDULE_IMPACT)
            .scheduleImpactComment(UPDATED_SCHEDULE_IMPACT_COMMENT)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        TasksDTO tasksDTO = tasksMapper.toDto(updatedTasks);

        restTasksMockMvc.perform(put("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isOk());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeUpdate);
        Tasks testTasks = tasksList.get(tasksList.size() - 1);
        assertThat(testTasks.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTasks.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTasks.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testTasks.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTasks.isCostImpact()).isEqualTo(UPDATED_COST_IMPACT);
        assertThat(testTasks.getCostImpactComment()).isEqualTo(UPDATED_COST_IMPACT_COMMENT);
        assertThat(testTasks.isScheduleImpact()).isEqualTo(UPDATED_SCHEDULE_IMPACT);
        assertThat(testTasks.getScheduleImpactComment()).isEqualTo(UPDATED_SCHEDULE_IMPACT_COMMENT);
        assertThat(testTasks.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTasks.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the Tasks in Elasticsearch
        verify(mockTasksSearchRepository, times(1)).save(testTasks);
    }

    @Test
    public void updateNonExistingTasks() throws Exception {
        int databaseSizeBeforeUpdate = tasksRepository.findAll().size();

        // Create the Tasks
        TasksDTO tasksDTO = tasksMapper.toDto(tasks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTasksMockMvc.perform(put("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Tasks in Elasticsearch
        verify(mockTasksSearchRepository, times(0)).save(tasks);
    }

    @Test
    public void deleteTasks() throws Exception {
        // Initialize the database
        tasksRepository.save(tasks);

        int databaseSizeBeforeDelete = tasksRepository.findAll().size();

        // Delete the tasks
        restTasksMockMvc.perform(delete("/api/tasks/{id}", tasks.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Tasks in Elasticsearch
        verify(mockTasksSearchRepository, times(1)).deleteById(tasks.getId());
    }

    @Test
    public void searchTasks() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        tasksRepository.save(tasks);
        when(mockTasksSearchRepository.search(queryStringQuery("id:" + tasks.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tasks), PageRequest.of(0, 1), 1));

        // Search the tasks
        restTasksMockMvc.perform(get("/api/_search/tasks?query=id:" + tasks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tasks.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].costImpact").value(hasItem(DEFAULT_COST_IMPACT.booleanValue())))
            .andExpect(jsonPath("$.[*].costImpactComment").value(hasItem(DEFAULT_COST_IMPACT_COMMENT)))
            .andExpect(jsonPath("$.[*].scheduleImpact").value(hasItem(DEFAULT_SCHEDULE_IMPACT.booleanValue())))
            .andExpect(jsonPath("$.[*].scheduleImpactComment").value(hasItem(DEFAULT_SCHEDULE_IMPACT_COMMENT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
