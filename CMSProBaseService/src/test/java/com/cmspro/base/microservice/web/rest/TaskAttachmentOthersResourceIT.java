package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.TaskAttachmentOthers;
import com.cmspro.base.microservice.repository.TaskAttachmentOthersRepository;
import com.cmspro.base.microservice.repository.search.TaskAttachmentOthersSearchRepository;
import com.cmspro.base.microservice.service.TaskAttachmentOthersService;
import com.cmspro.base.microservice.service.dto.TaskAttachmentOthersDTO;
import com.cmspro.base.microservice.service.mapper.TaskAttachmentOthersMapper;

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
import org.springframework.util.Base64Utils;

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
 * Integration tests for the {@link TaskAttachmentOthersResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskAttachmentOthersResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TYPE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TaskAttachmentOthersRepository taskAttachmentOthersRepository;

    @Autowired
    private TaskAttachmentOthersMapper taskAttachmentOthersMapper;

    @Autowired
    private TaskAttachmentOthersService taskAttachmentOthersService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.TaskAttachmentOthersSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaskAttachmentOthersSearchRepository mockTaskAttachmentOthersSearchRepository;

    @Autowired
    private MockMvc restTaskAttachmentOthersMockMvc;

    private TaskAttachmentOthers taskAttachmentOthers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskAttachmentOthers createEntity() {
        TaskAttachmentOthers taskAttachmentOthers = new TaskAttachmentOthers()
            .fileName(DEFAULT_FILE_NAME)
            .fileType(DEFAULT_FILE_TYPE)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return taskAttachmentOthers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskAttachmentOthers createUpdatedEntity() {
        TaskAttachmentOthers taskAttachmentOthers = new TaskAttachmentOthers()
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return taskAttachmentOthers;
    }

    @BeforeEach
    public void initTest() {
        taskAttachmentOthersRepository.deleteAll();
        taskAttachmentOthers = createEntity();
    }

    @Test
    public void createTaskAttachmentOthers() throws Exception {
        int databaseSizeBeforeCreate = taskAttachmentOthersRepository.findAll().size();
        // Create the TaskAttachmentOthers
        TaskAttachmentOthersDTO taskAttachmentOthersDTO = taskAttachmentOthersMapper.toDto(taskAttachmentOthers);
        restTaskAttachmentOthersMockMvc.perform(post("/api/task-attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentOthersDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskAttachmentOthers in the database
        List<TaskAttachmentOthers> taskAttachmentOthersList = taskAttachmentOthersRepository.findAll();
        assertThat(taskAttachmentOthersList).hasSize(databaseSizeBeforeCreate + 1);
        TaskAttachmentOthers testTaskAttachmentOthers = taskAttachmentOthersList.get(taskAttachmentOthersList.size() - 1);
        assertThat(testTaskAttachmentOthers.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testTaskAttachmentOthers.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testTaskAttachmentOthers.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testTaskAttachmentOthers.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testTaskAttachmentOthers.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTaskAttachmentOthers.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the TaskAttachmentOthers in Elasticsearch
        verify(mockTaskAttachmentOthersSearchRepository, times(1)).save(testTaskAttachmentOthers);
    }

    @Test
    public void createTaskAttachmentOthersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskAttachmentOthersRepository.findAll().size();

        // Create the TaskAttachmentOthers with an existing ID
        taskAttachmentOthers.setId("existing_id");
        TaskAttachmentOthersDTO taskAttachmentOthersDTO = taskAttachmentOthersMapper.toDto(taskAttachmentOthers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskAttachmentOthersMockMvc.perform(post("/api/task-attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentOthersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskAttachmentOthers in the database
        List<TaskAttachmentOthers> taskAttachmentOthersList = taskAttachmentOthersRepository.findAll();
        assertThat(taskAttachmentOthersList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaskAttachmentOthers in Elasticsearch
        verify(mockTaskAttachmentOthersSearchRepository, times(0)).save(taskAttachmentOthers);
    }


    @Test
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskAttachmentOthersRepository.findAll().size();
        // set the field null
        taskAttachmentOthers.setFileName(null);

        // Create the TaskAttachmentOthers, which fails.
        TaskAttachmentOthersDTO taskAttachmentOthersDTO = taskAttachmentOthersMapper.toDto(taskAttachmentOthers);


        restTaskAttachmentOthersMockMvc.perform(post("/api/task-attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentOthersDTO)))
            .andExpect(status().isBadRequest());

        List<TaskAttachmentOthers> taskAttachmentOthersList = taskAttachmentOthersRepository.findAll();
        assertThat(taskAttachmentOthersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskAttachmentOthersRepository.findAll().size();
        // set the field null
        taskAttachmentOthers.setCreatedBy(null);

        // Create the TaskAttachmentOthers, which fails.
        TaskAttachmentOthersDTO taskAttachmentOthersDTO = taskAttachmentOthersMapper.toDto(taskAttachmentOthers);


        restTaskAttachmentOthersMockMvc.perform(post("/api/task-attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentOthersDTO)))
            .andExpect(status().isBadRequest());

        List<TaskAttachmentOthers> taskAttachmentOthersList = taskAttachmentOthersRepository.findAll();
        assertThat(taskAttachmentOthersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskAttachmentOthersRepository.findAll().size();
        // set the field null
        taskAttachmentOthers.setCreationDate(null);

        // Create the TaskAttachmentOthers, which fails.
        TaskAttachmentOthersDTO taskAttachmentOthersDTO = taskAttachmentOthersMapper.toDto(taskAttachmentOthers);


        restTaskAttachmentOthersMockMvc.perform(post("/api/task-attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentOthersDTO)))
            .andExpect(status().isBadRequest());

        List<TaskAttachmentOthers> taskAttachmentOthersList = taskAttachmentOthersRepository.findAll();
        assertThat(taskAttachmentOthersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTaskAttachmentOthers() throws Exception {
        // Initialize the database
        taskAttachmentOthersRepository.save(taskAttachmentOthers);

        // Get all the taskAttachmentOthersList
        restTaskAttachmentOthersMockMvc.perform(get("/api/task-attachment-others?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskAttachmentOthers.getId())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getTaskAttachmentOthers() throws Exception {
        // Initialize the database
        taskAttachmentOthersRepository.save(taskAttachmentOthers);

        // Get the taskAttachmentOthers
        restTaskAttachmentOthersMockMvc.perform(get("/api/task-attachment-others/{id}", taskAttachmentOthers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskAttachmentOthers.getId()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingTaskAttachmentOthers() throws Exception {
        // Get the taskAttachmentOthers
        restTaskAttachmentOthersMockMvc.perform(get("/api/task-attachment-others/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTaskAttachmentOthers() throws Exception {
        // Initialize the database
        taskAttachmentOthersRepository.save(taskAttachmentOthers);

        int databaseSizeBeforeUpdate = taskAttachmentOthersRepository.findAll().size();

        // Update the taskAttachmentOthers
        TaskAttachmentOthers updatedTaskAttachmentOthers = taskAttachmentOthersRepository.findById(taskAttachmentOthers.getId()).get();
        updatedTaskAttachmentOthers
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        TaskAttachmentOthersDTO taskAttachmentOthersDTO = taskAttachmentOthersMapper.toDto(updatedTaskAttachmentOthers);

        restTaskAttachmentOthersMockMvc.perform(put("/api/task-attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentOthersDTO)))
            .andExpect(status().isOk());

        // Validate the TaskAttachmentOthers in the database
        List<TaskAttachmentOthers> taskAttachmentOthersList = taskAttachmentOthersRepository.findAll();
        assertThat(taskAttachmentOthersList).hasSize(databaseSizeBeforeUpdate);
        TaskAttachmentOthers testTaskAttachmentOthers = taskAttachmentOthersList.get(taskAttachmentOthersList.size() - 1);
        assertThat(testTaskAttachmentOthers.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testTaskAttachmentOthers.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testTaskAttachmentOthers.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTaskAttachmentOthers.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testTaskAttachmentOthers.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTaskAttachmentOthers.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the TaskAttachmentOthers in Elasticsearch
        verify(mockTaskAttachmentOthersSearchRepository, times(1)).save(testTaskAttachmentOthers);
    }

    @Test
    public void updateNonExistingTaskAttachmentOthers() throws Exception {
        int databaseSizeBeforeUpdate = taskAttachmentOthersRepository.findAll().size();

        // Create the TaskAttachmentOthers
        TaskAttachmentOthersDTO taskAttachmentOthersDTO = taskAttachmentOthersMapper.toDto(taskAttachmentOthers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskAttachmentOthersMockMvc.perform(put("/api/task-attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentOthersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskAttachmentOthers in the database
        List<TaskAttachmentOthers> taskAttachmentOthersList = taskAttachmentOthersRepository.findAll();
        assertThat(taskAttachmentOthersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaskAttachmentOthers in Elasticsearch
        verify(mockTaskAttachmentOthersSearchRepository, times(0)).save(taskAttachmentOthers);
    }

    @Test
    public void deleteTaskAttachmentOthers() throws Exception {
        // Initialize the database
        taskAttachmentOthersRepository.save(taskAttachmentOthers);

        int databaseSizeBeforeDelete = taskAttachmentOthersRepository.findAll().size();

        // Delete the taskAttachmentOthers
        restTaskAttachmentOthersMockMvc.perform(delete("/api/task-attachment-others/{id}", taskAttachmentOthers.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskAttachmentOthers> taskAttachmentOthersList = taskAttachmentOthersRepository.findAll();
        assertThat(taskAttachmentOthersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaskAttachmentOthers in Elasticsearch
        verify(mockTaskAttachmentOthersSearchRepository, times(1)).deleteById(taskAttachmentOthers.getId());
    }

    @Test
    public void searchTaskAttachmentOthers() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        taskAttachmentOthersRepository.save(taskAttachmentOthers);
        when(mockTaskAttachmentOthersSearchRepository.search(queryStringQuery("id:" + taskAttachmentOthers.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taskAttachmentOthers), PageRequest.of(0, 1), 1));

        // Search the taskAttachmentOthers
        restTaskAttachmentOthersMockMvc.perform(get("/api/_search/task-attachment-others?query=id:" + taskAttachmentOthers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskAttachmentOthers.getId())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
