package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.TaskAttachmentImages;
import com.cmspro.base.microservice.repository.TaskAttachmentImagesRepository;
import com.cmspro.base.microservice.repository.search.TaskAttachmentImagesSearchRepository;
import com.cmspro.base.microservice.service.TaskAttachmentImagesService;
import com.cmspro.base.microservice.service.dto.TaskAttachmentImagesDTO;
import com.cmspro.base.microservice.service.mapper.TaskAttachmentImagesMapper;

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
 * Integration tests for the {@link TaskAttachmentImagesResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskAttachmentImagesResourceIT {

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
    private TaskAttachmentImagesRepository taskAttachmentImagesRepository;

    @Autowired
    private TaskAttachmentImagesMapper taskAttachmentImagesMapper;

    @Autowired
    private TaskAttachmentImagesService taskAttachmentImagesService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.TaskAttachmentImagesSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaskAttachmentImagesSearchRepository mockTaskAttachmentImagesSearchRepository;

    @Autowired
    private MockMvc restTaskAttachmentImagesMockMvc;

    private TaskAttachmentImages taskAttachmentImages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskAttachmentImages createEntity() {
        TaskAttachmentImages taskAttachmentImages = new TaskAttachmentImages()
            .fileName(DEFAULT_FILE_NAME)
            .fileType(DEFAULT_FILE_TYPE)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return taskAttachmentImages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskAttachmentImages createUpdatedEntity() {
        TaskAttachmentImages taskAttachmentImages = new TaskAttachmentImages()
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return taskAttachmentImages;
    }

    @BeforeEach
    public void initTest() {
        taskAttachmentImagesRepository.deleteAll();
        taskAttachmentImages = createEntity();
    }

    @Test
    public void createTaskAttachmentImages() throws Exception {
        int databaseSizeBeforeCreate = taskAttachmentImagesRepository.findAll().size();
        // Create the TaskAttachmentImages
        TaskAttachmentImagesDTO taskAttachmentImagesDTO = taskAttachmentImagesMapper.toDto(taskAttachmentImages);
        restTaskAttachmentImagesMockMvc.perform(post("/api/task-attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentImagesDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskAttachmentImages in the database
        List<TaskAttachmentImages> taskAttachmentImagesList = taskAttachmentImagesRepository.findAll();
        assertThat(taskAttachmentImagesList).hasSize(databaseSizeBeforeCreate + 1);
        TaskAttachmentImages testTaskAttachmentImages = taskAttachmentImagesList.get(taskAttachmentImagesList.size() - 1);
        assertThat(testTaskAttachmentImages.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testTaskAttachmentImages.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testTaskAttachmentImages.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testTaskAttachmentImages.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testTaskAttachmentImages.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTaskAttachmentImages.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the TaskAttachmentImages in Elasticsearch
        verify(mockTaskAttachmentImagesSearchRepository, times(1)).save(testTaskAttachmentImages);
    }

    @Test
    public void createTaskAttachmentImagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskAttachmentImagesRepository.findAll().size();

        // Create the TaskAttachmentImages with an existing ID
        taskAttachmentImages.setId("existing_id");
        TaskAttachmentImagesDTO taskAttachmentImagesDTO = taskAttachmentImagesMapper.toDto(taskAttachmentImages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskAttachmentImagesMockMvc.perform(post("/api/task-attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentImagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskAttachmentImages in the database
        List<TaskAttachmentImages> taskAttachmentImagesList = taskAttachmentImagesRepository.findAll();
        assertThat(taskAttachmentImagesList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaskAttachmentImages in Elasticsearch
        verify(mockTaskAttachmentImagesSearchRepository, times(0)).save(taskAttachmentImages);
    }


    @Test
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskAttachmentImagesRepository.findAll().size();
        // set the field null
        taskAttachmentImages.setFileName(null);

        // Create the TaskAttachmentImages, which fails.
        TaskAttachmentImagesDTO taskAttachmentImagesDTO = taskAttachmentImagesMapper.toDto(taskAttachmentImages);


        restTaskAttachmentImagesMockMvc.perform(post("/api/task-attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentImagesDTO)))
            .andExpect(status().isBadRequest());

        List<TaskAttachmentImages> taskAttachmentImagesList = taskAttachmentImagesRepository.findAll();
        assertThat(taskAttachmentImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskAttachmentImagesRepository.findAll().size();
        // set the field null
        taskAttachmentImages.setCreatedBy(null);

        // Create the TaskAttachmentImages, which fails.
        TaskAttachmentImagesDTO taskAttachmentImagesDTO = taskAttachmentImagesMapper.toDto(taskAttachmentImages);


        restTaskAttachmentImagesMockMvc.perform(post("/api/task-attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentImagesDTO)))
            .andExpect(status().isBadRequest());

        List<TaskAttachmentImages> taskAttachmentImagesList = taskAttachmentImagesRepository.findAll();
        assertThat(taskAttachmentImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskAttachmentImagesRepository.findAll().size();
        // set the field null
        taskAttachmentImages.setCreationDate(null);

        // Create the TaskAttachmentImages, which fails.
        TaskAttachmentImagesDTO taskAttachmentImagesDTO = taskAttachmentImagesMapper.toDto(taskAttachmentImages);


        restTaskAttachmentImagesMockMvc.perform(post("/api/task-attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentImagesDTO)))
            .andExpect(status().isBadRequest());

        List<TaskAttachmentImages> taskAttachmentImagesList = taskAttachmentImagesRepository.findAll();
        assertThat(taskAttachmentImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTaskAttachmentImages() throws Exception {
        // Initialize the database
        taskAttachmentImagesRepository.save(taskAttachmentImages);

        // Get all the taskAttachmentImagesList
        restTaskAttachmentImagesMockMvc.perform(get("/api/task-attachment-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskAttachmentImages.getId())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getTaskAttachmentImages() throws Exception {
        // Initialize the database
        taskAttachmentImagesRepository.save(taskAttachmentImages);

        // Get the taskAttachmentImages
        restTaskAttachmentImagesMockMvc.perform(get("/api/task-attachment-images/{id}", taskAttachmentImages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskAttachmentImages.getId()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingTaskAttachmentImages() throws Exception {
        // Get the taskAttachmentImages
        restTaskAttachmentImagesMockMvc.perform(get("/api/task-attachment-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTaskAttachmentImages() throws Exception {
        // Initialize the database
        taskAttachmentImagesRepository.save(taskAttachmentImages);

        int databaseSizeBeforeUpdate = taskAttachmentImagesRepository.findAll().size();

        // Update the taskAttachmentImages
        TaskAttachmentImages updatedTaskAttachmentImages = taskAttachmentImagesRepository.findById(taskAttachmentImages.getId()).get();
        updatedTaskAttachmentImages
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        TaskAttachmentImagesDTO taskAttachmentImagesDTO = taskAttachmentImagesMapper.toDto(updatedTaskAttachmentImages);

        restTaskAttachmentImagesMockMvc.perform(put("/api/task-attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentImagesDTO)))
            .andExpect(status().isOk());

        // Validate the TaskAttachmentImages in the database
        List<TaskAttachmentImages> taskAttachmentImagesList = taskAttachmentImagesRepository.findAll();
        assertThat(taskAttachmentImagesList).hasSize(databaseSizeBeforeUpdate);
        TaskAttachmentImages testTaskAttachmentImages = taskAttachmentImagesList.get(taskAttachmentImagesList.size() - 1);
        assertThat(testTaskAttachmentImages.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testTaskAttachmentImages.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testTaskAttachmentImages.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTaskAttachmentImages.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testTaskAttachmentImages.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTaskAttachmentImages.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the TaskAttachmentImages in Elasticsearch
        verify(mockTaskAttachmentImagesSearchRepository, times(1)).save(testTaskAttachmentImages);
    }

    @Test
    public void updateNonExistingTaskAttachmentImages() throws Exception {
        int databaseSizeBeforeUpdate = taskAttachmentImagesRepository.findAll().size();

        // Create the TaskAttachmentImages
        TaskAttachmentImagesDTO taskAttachmentImagesDTO = taskAttachmentImagesMapper.toDto(taskAttachmentImages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskAttachmentImagesMockMvc.perform(put("/api/task-attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskAttachmentImagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskAttachmentImages in the database
        List<TaskAttachmentImages> taskAttachmentImagesList = taskAttachmentImagesRepository.findAll();
        assertThat(taskAttachmentImagesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaskAttachmentImages in Elasticsearch
        verify(mockTaskAttachmentImagesSearchRepository, times(0)).save(taskAttachmentImages);
    }

    @Test
    public void deleteTaskAttachmentImages() throws Exception {
        // Initialize the database
        taskAttachmentImagesRepository.save(taskAttachmentImages);

        int databaseSizeBeforeDelete = taskAttachmentImagesRepository.findAll().size();

        // Delete the taskAttachmentImages
        restTaskAttachmentImagesMockMvc.perform(delete("/api/task-attachment-images/{id}", taskAttachmentImages.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskAttachmentImages> taskAttachmentImagesList = taskAttachmentImagesRepository.findAll();
        assertThat(taskAttachmentImagesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaskAttachmentImages in Elasticsearch
        verify(mockTaskAttachmentImagesSearchRepository, times(1)).deleteById(taskAttachmentImages.getId());
    }

    @Test
    public void searchTaskAttachmentImages() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        taskAttachmentImagesRepository.save(taskAttachmentImages);
        when(mockTaskAttachmentImagesSearchRepository.search(queryStringQuery("id:" + taskAttachmentImages.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taskAttachmentImages), PageRequest.of(0, 1), 1));

        // Search the taskAttachmentImages
        restTaskAttachmentImagesMockMvc.perform(get("/api/_search/task-attachment-images?query=id:" + taskAttachmentImages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskAttachmentImages.getId())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
