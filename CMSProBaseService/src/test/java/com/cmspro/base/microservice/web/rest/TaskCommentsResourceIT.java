package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.TaskComments;
import com.cmspro.base.microservice.repository.TaskCommentsRepository;
import com.cmspro.base.microservice.repository.search.TaskCommentsSearchRepository;
import com.cmspro.base.microservice.service.TaskCommentsService;
import com.cmspro.base.microservice.service.dto.TaskCommentsDTO;
import com.cmspro.base.microservice.service.mapper.TaskCommentsMapper;

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
 * Integration tests for the {@link TaskCommentsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskCommentsResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TaskCommentsRepository taskCommentsRepository;

    @Autowired
    private TaskCommentsMapper taskCommentsMapper;

    @Autowired
    private TaskCommentsService taskCommentsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.TaskCommentsSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaskCommentsSearchRepository mockTaskCommentsSearchRepository;

    @Autowired
    private MockMvc restTaskCommentsMockMvc;

    private TaskComments taskComments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskComments createEntity() {
        TaskComments taskComments = new TaskComments()
            .createdBy(DEFAULT_CREATED_BY)
            .comment(DEFAULT_COMMENT)
            .creationDate(DEFAULT_CREATION_DATE);
        return taskComments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskComments createUpdatedEntity() {
        TaskComments taskComments = new TaskComments()
            .createdBy(UPDATED_CREATED_BY)
            .comment(UPDATED_COMMENT)
            .creationDate(UPDATED_CREATION_DATE);
        return taskComments;
    }

    @BeforeEach
    public void initTest() {
        taskCommentsRepository.deleteAll();
        taskComments = createEntity();
    }

    @Test
    public void createTaskComments() throws Exception {
        int databaseSizeBeforeCreate = taskCommentsRepository.findAll().size();
        // Create the TaskComments
        TaskCommentsDTO taskCommentsDTO = taskCommentsMapper.toDto(taskComments);
        restTaskCommentsMockMvc.perform(post("/api/task-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCommentsDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskComments in the database
        List<TaskComments> taskCommentsList = taskCommentsRepository.findAll();
        assertThat(taskCommentsList).hasSize(databaseSizeBeforeCreate + 1);
        TaskComments testTaskComments = taskCommentsList.get(taskCommentsList.size() - 1);
        assertThat(testTaskComments.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTaskComments.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTaskComments.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the TaskComments in Elasticsearch
        verify(mockTaskCommentsSearchRepository, times(1)).save(testTaskComments);
    }

    @Test
    public void createTaskCommentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskCommentsRepository.findAll().size();

        // Create the TaskComments with an existing ID
        taskComments.setId("existing_id");
        TaskCommentsDTO taskCommentsDTO = taskCommentsMapper.toDto(taskComments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskCommentsMockMvc.perform(post("/api/task-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCommentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskComments in the database
        List<TaskComments> taskCommentsList = taskCommentsRepository.findAll();
        assertThat(taskCommentsList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaskComments in Elasticsearch
        verify(mockTaskCommentsSearchRepository, times(0)).save(taskComments);
    }


    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCommentsRepository.findAll().size();
        // set the field null
        taskComments.setCreatedBy(null);

        // Create the TaskComments, which fails.
        TaskCommentsDTO taskCommentsDTO = taskCommentsMapper.toDto(taskComments);


        restTaskCommentsMockMvc.perform(post("/api/task-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCommentsDTO)))
            .andExpect(status().isBadRequest());

        List<TaskComments> taskCommentsList = taskCommentsRepository.findAll();
        assertThat(taskCommentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCommentsRepository.findAll().size();
        // set the field null
        taskComments.setComment(null);

        // Create the TaskComments, which fails.
        TaskCommentsDTO taskCommentsDTO = taskCommentsMapper.toDto(taskComments);


        restTaskCommentsMockMvc.perform(post("/api/task-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCommentsDTO)))
            .andExpect(status().isBadRequest());

        List<TaskComments> taskCommentsList = taskCommentsRepository.findAll();
        assertThat(taskCommentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskCommentsRepository.findAll().size();
        // set the field null
        taskComments.setCreationDate(null);

        // Create the TaskComments, which fails.
        TaskCommentsDTO taskCommentsDTO = taskCommentsMapper.toDto(taskComments);


        restTaskCommentsMockMvc.perform(post("/api/task-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCommentsDTO)))
            .andExpect(status().isBadRequest());

        List<TaskComments> taskCommentsList = taskCommentsRepository.findAll();
        assertThat(taskCommentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTaskComments() throws Exception {
        // Initialize the database
        taskCommentsRepository.save(taskComments);

        // Get all the taskCommentsList
        restTaskCommentsMockMvc.perform(get("/api/task-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskComments.getId())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getTaskComments() throws Exception {
        // Initialize the database
        taskCommentsRepository.save(taskComments);

        // Get the taskComments
        restTaskCommentsMockMvc.perform(get("/api/task-comments/{id}", taskComments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskComments.getId()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingTaskComments() throws Exception {
        // Get the taskComments
        restTaskCommentsMockMvc.perform(get("/api/task-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTaskComments() throws Exception {
        // Initialize the database
        taskCommentsRepository.save(taskComments);

        int databaseSizeBeforeUpdate = taskCommentsRepository.findAll().size();

        // Update the taskComments
        TaskComments updatedTaskComments = taskCommentsRepository.findById(taskComments.getId()).get();
        updatedTaskComments
            .createdBy(UPDATED_CREATED_BY)
            .comment(UPDATED_COMMENT)
            .creationDate(UPDATED_CREATION_DATE);
        TaskCommentsDTO taskCommentsDTO = taskCommentsMapper.toDto(updatedTaskComments);

        restTaskCommentsMockMvc.perform(put("/api/task-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCommentsDTO)))
            .andExpect(status().isOk());

        // Validate the TaskComments in the database
        List<TaskComments> taskCommentsList = taskCommentsRepository.findAll();
        assertThat(taskCommentsList).hasSize(databaseSizeBeforeUpdate);
        TaskComments testTaskComments = taskCommentsList.get(taskCommentsList.size() - 1);
        assertThat(testTaskComments.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTaskComments.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTaskComments.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the TaskComments in Elasticsearch
        verify(mockTaskCommentsSearchRepository, times(1)).save(testTaskComments);
    }

    @Test
    public void updateNonExistingTaskComments() throws Exception {
        int databaseSizeBeforeUpdate = taskCommentsRepository.findAll().size();

        // Create the TaskComments
        TaskCommentsDTO taskCommentsDTO = taskCommentsMapper.toDto(taskComments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskCommentsMockMvc.perform(put("/api/task-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskCommentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskComments in the database
        List<TaskComments> taskCommentsList = taskCommentsRepository.findAll();
        assertThat(taskCommentsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaskComments in Elasticsearch
        verify(mockTaskCommentsSearchRepository, times(0)).save(taskComments);
    }

    @Test
    public void deleteTaskComments() throws Exception {
        // Initialize the database
        taskCommentsRepository.save(taskComments);

        int databaseSizeBeforeDelete = taskCommentsRepository.findAll().size();

        // Delete the taskComments
        restTaskCommentsMockMvc.perform(delete("/api/task-comments/{id}", taskComments.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskComments> taskCommentsList = taskCommentsRepository.findAll();
        assertThat(taskCommentsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaskComments in Elasticsearch
        verify(mockTaskCommentsSearchRepository, times(1)).deleteById(taskComments.getId());
    }

    @Test
    public void searchTaskComments() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        taskCommentsRepository.save(taskComments);
        when(mockTaskCommentsSearchRepository.search(queryStringQuery("id:" + taskComments.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taskComments), PageRequest.of(0, 1), 1));

        // Search the taskComments
        restTaskCommentsMockMvc.perform(get("/api/_search/task-comments?query=id:" + taskComments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskComments.getId())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
