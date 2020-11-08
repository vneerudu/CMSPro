package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.TaskAttachments;
import com.cmspro.base.microservice.repository.TaskAttachmentsRepository;
import com.cmspro.base.microservice.repository.search.TaskAttachmentsSearchRepository;
import com.cmspro.base.microservice.service.TaskAttachmentsService;
import com.cmspro.base.microservice.service.dto.TaskAttachmentsDTO;
import com.cmspro.base.microservice.service.mapper.TaskAttachmentsMapper;

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
 * Integration tests for the {@link TaskAttachmentsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskAttachmentsResourceIT {

    private static final String DEFAULT_FOLDER = "AAAAAAAAAA";
    private static final String UPDATED_FOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TaskAttachmentsRepository taskAttachmentsRepository;

    @Autowired
    private TaskAttachmentsMapper taskAttachmentsMapper;

    @Autowired
    private TaskAttachmentsService taskAttachmentsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.TaskAttachmentsSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaskAttachmentsSearchRepository mockTaskAttachmentsSearchRepository;

    @Autowired
    private MockMvc restTaskAttachmentsMockMvc;

    private TaskAttachments taskAttachments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskAttachments createEntity() {
        TaskAttachments taskAttachments = new TaskAttachments()
            .folder(DEFAULT_FOLDER)
            .fileName(DEFAULT_FILE_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return taskAttachments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskAttachments createUpdatedEntity() {
        TaskAttachments taskAttachments = new TaskAttachments()
            .folder(UPDATED_FOLDER)
            .fileName(UPDATED_FILE_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return taskAttachments;
    }

    @BeforeEach
    public void initTest() {
        taskAttachmentsRepository.deleteAll();
        taskAttachments = createEntity();
    }

    @Test
    public void createTaskAttachments() throws Exception {
        int databaseSizeBeforeCreate = taskAttachmentsRepository.findAll().size();
        // Create the TaskAttachments
        TaskAttachmentsDTO taskAttachmentsDTO = taskAttachmentsMapper.toDto(taskAttachments);
        restTaskAttachmentsMockMvc.perform(post("/api/task-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentsDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskAttachments in the database
        List<TaskAttachments> taskAttachmentsList = taskAttachmentsRepository.findAll();
        assertThat(taskAttachmentsList).hasSize(databaseSizeBeforeCreate + 1);
        TaskAttachments testTaskAttachments = taskAttachmentsList.get(taskAttachmentsList.size() - 1);
        assertThat(testTaskAttachments.getFolder()).isEqualTo(DEFAULT_FOLDER);
        assertThat(testTaskAttachments.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testTaskAttachments.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTaskAttachments.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the TaskAttachments in Elasticsearch
        verify(mockTaskAttachmentsSearchRepository, times(1)).save(testTaskAttachments);
    }

    @Test
    public void createTaskAttachmentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskAttachmentsRepository.findAll().size();

        // Create the TaskAttachments with an existing ID
        taskAttachments.setId("existing_id");
        TaskAttachmentsDTO taskAttachmentsDTO = taskAttachmentsMapper.toDto(taskAttachments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskAttachmentsMockMvc.perform(post("/api/task-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskAttachments in the database
        List<TaskAttachments> taskAttachmentsList = taskAttachmentsRepository.findAll();
        assertThat(taskAttachmentsList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaskAttachments in Elasticsearch
        verify(mockTaskAttachmentsSearchRepository, times(0)).save(taskAttachments);
    }


    @Test
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskAttachmentsRepository.findAll().size();
        // set the field null
        taskAttachments.setFileName(null);

        // Create the TaskAttachments, which fails.
        TaskAttachmentsDTO taskAttachmentsDTO = taskAttachmentsMapper.toDto(taskAttachments);


        restTaskAttachmentsMockMvc.perform(post("/api/task-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentsDTO)))
            .andExpect(status().isBadRequest());

        List<TaskAttachments> taskAttachmentsList = taskAttachmentsRepository.findAll();
        assertThat(taskAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskAttachmentsRepository.findAll().size();
        // set the field null
        taskAttachments.setCreatedBy(null);

        // Create the TaskAttachments, which fails.
        TaskAttachmentsDTO taskAttachmentsDTO = taskAttachmentsMapper.toDto(taskAttachments);


        restTaskAttachmentsMockMvc.perform(post("/api/task-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentsDTO)))
            .andExpect(status().isBadRequest());

        List<TaskAttachments> taskAttachmentsList = taskAttachmentsRepository.findAll();
        assertThat(taskAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskAttachmentsRepository.findAll().size();
        // set the field null
        taskAttachments.setCreationDate(null);

        // Create the TaskAttachments, which fails.
        TaskAttachmentsDTO taskAttachmentsDTO = taskAttachmentsMapper.toDto(taskAttachments);


        restTaskAttachmentsMockMvc.perform(post("/api/task-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentsDTO)))
            .andExpect(status().isBadRequest());

        List<TaskAttachments> taskAttachmentsList = taskAttachmentsRepository.findAll();
        assertThat(taskAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTaskAttachments() throws Exception {
        // Initialize the database
        taskAttachmentsRepository.save(taskAttachments);

        // Get all the taskAttachmentsList
        restTaskAttachmentsMockMvc.perform(get("/api/task-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskAttachments.getId())))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getTaskAttachments() throws Exception {
        // Initialize the database
        taskAttachmentsRepository.save(taskAttachments);

        // Get the taskAttachments
        restTaskAttachmentsMockMvc.perform(get("/api/task-attachments/{id}", taskAttachments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskAttachments.getId()))
            .andExpect(jsonPath("$.folder").value(DEFAULT_FOLDER))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingTaskAttachments() throws Exception {
        // Get the taskAttachments
        restTaskAttachmentsMockMvc.perform(get("/api/task-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTaskAttachments() throws Exception {
        // Initialize the database
        taskAttachmentsRepository.save(taskAttachments);

        int databaseSizeBeforeUpdate = taskAttachmentsRepository.findAll().size();

        // Update the taskAttachments
        TaskAttachments updatedTaskAttachments = taskAttachmentsRepository.findById(taskAttachments.getId()).get();
        updatedTaskAttachments
            .folder(UPDATED_FOLDER)
            .fileName(UPDATED_FILE_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        TaskAttachmentsDTO taskAttachmentsDTO = taskAttachmentsMapper.toDto(updatedTaskAttachments);

        restTaskAttachmentsMockMvc.perform(put("/api/task-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentsDTO)))
            .andExpect(status().isOk());

        // Validate the TaskAttachments in the database
        List<TaskAttachments> taskAttachmentsList = taskAttachmentsRepository.findAll();
        assertThat(taskAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        TaskAttachments testTaskAttachments = taskAttachmentsList.get(taskAttachmentsList.size() - 1);
        assertThat(testTaskAttachments.getFolder()).isEqualTo(UPDATED_FOLDER);
        assertThat(testTaskAttachments.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testTaskAttachments.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTaskAttachments.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the TaskAttachments in Elasticsearch
        verify(mockTaskAttachmentsSearchRepository, times(1)).save(testTaskAttachments);
    }

    @Test
    public void updateNonExistingTaskAttachments() throws Exception {
        int databaseSizeBeforeUpdate = taskAttachmentsRepository.findAll().size();

        // Create the TaskAttachments
        TaskAttachmentsDTO taskAttachmentsDTO = taskAttachmentsMapper.toDto(taskAttachments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskAttachmentsMockMvc.perform(put("/api/task-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskAttachments in the database
        List<TaskAttachments> taskAttachmentsList = taskAttachmentsRepository.findAll();
        assertThat(taskAttachmentsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaskAttachments in Elasticsearch
        verify(mockTaskAttachmentsSearchRepository, times(0)).save(taskAttachments);
    }

    @Test
    public void deleteTaskAttachments() throws Exception {
        // Initialize the database
        taskAttachmentsRepository.save(taskAttachments);

        int databaseSizeBeforeDelete = taskAttachmentsRepository.findAll().size();

        // Delete the taskAttachments
        restTaskAttachmentsMockMvc.perform(delete("/api/task-attachments/{id}", taskAttachments.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskAttachments> taskAttachmentsList = taskAttachmentsRepository.findAll();
        assertThat(taskAttachmentsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaskAttachments in Elasticsearch
        verify(mockTaskAttachmentsSearchRepository, times(1)).deleteById(taskAttachments.getId());
    }

    @Test
    public void searchTaskAttachments() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        taskAttachmentsRepository.save(taskAttachments);
        when(mockTaskAttachmentsSearchRepository.search(queryStringQuery("id:" + taskAttachments.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taskAttachments), PageRequest.of(0, 1), 1));

        // Search the taskAttachments
        restTaskAttachmentsMockMvc.perform(get("/api/_search/task-attachments?query=id:" + taskAttachments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskAttachments.getId())))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
