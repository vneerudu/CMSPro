package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.AttachmentImages;
import com.cmspro.base.microservice.repository.AttachmentImagesRepository;
import com.cmspro.base.microservice.repository.search.AttachmentImagesSearchRepository;
import com.cmspro.base.microservice.service.AttachmentImagesService;
import com.cmspro.base.microservice.service.dto.AttachmentImagesDTO;
import com.cmspro.base.microservice.service.mapper.AttachmentImagesMapper;

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
 * Integration tests for the {@link AttachmentImagesResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttachmentImagesResourceIT {

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
    private AttachmentImagesRepository attachmentImagesRepository;

    @Autowired
    private AttachmentImagesMapper attachmentImagesMapper;

    @Autowired
    private AttachmentImagesService attachmentImagesService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.AttachmentImagesSearchRepositoryMockConfiguration
     */
    @Autowired
    private AttachmentImagesSearchRepository mockAttachmentImagesSearchRepository;

    @Autowired
    private MockMvc restAttachmentImagesMockMvc;

    private AttachmentImages attachmentImages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachmentImages createEntity() {
        AttachmentImages attachmentImages = new AttachmentImages()
            .fileName(DEFAULT_FILE_NAME)
            .fileType(DEFAULT_FILE_TYPE)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return attachmentImages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachmentImages createUpdatedEntity() {
        AttachmentImages attachmentImages = new AttachmentImages()
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return attachmentImages;
    }

    @BeforeEach
    public void initTest() {
        attachmentImagesRepository.deleteAll();
        attachmentImages = createEntity();
    }

    @Test
    public void createAttachmentImages() throws Exception {
        int databaseSizeBeforeCreate = attachmentImagesRepository.findAll().size();
        // Create the AttachmentImages
        AttachmentImagesDTO attachmentImagesDTO = attachmentImagesMapper.toDto(attachmentImages);
        restAttachmentImagesMockMvc.perform(post("/api/attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentImagesDTO)))
            .andExpect(status().isCreated());

        // Validate the AttachmentImages in the database
        List<AttachmentImages> attachmentImagesList = attachmentImagesRepository.findAll();
        assertThat(attachmentImagesList).hasSize(databaseSizeBeforeCreate + 1);
        AttachmentImages testAttachmentImages = attachmentImagesList.get(attachmentImagesList.size() - 1);
        assertThat(testAttachmentImages.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testAttachmentImages.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testAttachmentImages.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testAttachmentImages.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testAttachmentImages.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAttachmentImages.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the AttachmentImages in Elasticsearch
        verify(mockAttachmentImagesSearchRepository, times(1)).save(testAttachmentImages);
    }

    @Test
    public void createAttachmentImagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attachmentImagesRepository.findAll().size();

        // Create the AttachmentImages with an existing ID
        attachmentImages.setId("existing_id");
        AttachmentImagesDTO attachmentImagesDTO = attachmentImagesMapper.toDto(attachmentImages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachmentImagesMockMvc.perform(post("/api/attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentImagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttachmentImages in the database
        List<AttachmentImages> attachmentImagesList = attachmentImagesRepository.findAll();
        assertThat(attachmentImagesList).hasSize(databaseSizeBeforeCreate);

        // Validate the AttachmentImages in Elasticsearch
        verify(mockAttachmentImagesSearchRepository, times(0)).save(attachmentImages);
    }


    @Test
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentImagesRepository.findAll().size();
        // set the field null
        attachmentImages.setFileName(null);

        // Create the AttachmentImages, which fails.
        AttachmentImagesDTO attachmentImagesDTO = attachmentImagesMapper.toDto(attachmentImages);


        restAttachmentImagesMockMvc.perform(post("/api/attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentImagesDTO)))
            .andExpect(status().isBadRequest());

        List<AttachmentImages> attachmentImagesList = attachmentImagesRepository.findAll();
        assertThat(attachmentImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentImagesRepository.findAll().size();
        // set the field null
        attachmentImages.setCreatedBy(null);

        // Create the AttachmentImages, which fails.
        AttachmentImagesDTO attachmentImagesDTO = attachmentImagesMapper.toDto(attachmentImages);


        restAttachmentImagesMockMvc.perform(post("/api/attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentImagesDTO)))
            .andExpect(status().isBadRequest());

        List<AttachmentImages> attachmentImagesList = attachmentImagesRepository.findAll();
        assertThat(attachmentImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentImagesRepository.findAll().size();
        // set the field null
        attachmentImages.setCreationDate(null);

        // Create the AttachmentImages, which fails.
        AttachmentImagesDTO attachmentImagesDTO = attachmentImagesMapper.toDto(attachmentImages);


        restAttachmentImagesMockMvc.perform(post("/api/attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentImagesDTO)))
            .andExpect(status().isBadRequest());

        List<AttachmentImages> attachmentImagesList = attachmentImagesRepository.findAll();
        assertThat(attachmentImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAttachmentImages() throws Exception {
        // Initialize the database
        attachmentImagesRepository.save(attachmentImages);

        // Get all the attachmentImagesList
        restAttachmentImagesMockMvc.perform(get("/api/attachment-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachmentImages.getId())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getAttachmentImages() throws Exception {
        // Initialize the database
        attachmentImagesRepository.save(attachmentImages);

        // Get the attachmentImages
        restAttachmentImagesMockMvc.perform(get("/api/attachment-images/{id}", attachmentImages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attachmentImages.getId()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingAttachmentImages() throws Exception {
        // Get the attachmentImages
        restAttachmentImagesMockMvc.perform(get("/api/attachment-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAttachmentImages() throws Exception {
        // Initialize the database
        attachmentImagesRepository.save(attachmentImages);

        int databaseSizeBeforeUpdate = attachmentImagesRepository.findAll().size();

        // Update the attachmentImages
        AttachmentImages updatedAttachmentImages = attachmentImagesRepository.findById(attachmentImages.getId()).get();
        updatedAttachmentImages
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        AttachmentImagesDTO attachmentImagesDTO = attachmentImagesMapper.toDto(updatedAttachmentImages);

        restAttachmentImagesMockMvc.perform(put("/api/attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentImagesDTO)))
            .andExpect(status().isOk());

        // Validate the AttachmentImages in the database
        List<AttachmentImages> attachmentImagesList = attachmentImagesRepository.findAll();
        assertThat(attachmentImagesList).hasSize(databaseSizeBeforeUpdate);
        AttachmentImages testAttachmentImages = attachmentImagesList.get(attachmentImagesList.size() - 1);
        assertThat(testAttachmentImages.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testAttachmentImages.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testAttachmentImages.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAttachmentImages.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testAttachmentImages.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttachmentImages.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the AttachmentImages in Elasticsearch
        verify(mockAttachmentImagesSearchRepository, times(1)).save(testAttachmentImages);
    }

    @Test
    public void updateNonExistingAttachmentImages() throws Exception {
        int databaseSizeBeforeUpdate = attachmentImagesRepository.findAll().size();

        // Create the AttachmentImages
        AttachmentImagesDTO attachmentImagesDTO = attachmentImagesMapper.toDto(attachmentImages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentImagesMockMvc.perform(put("/api/attachment-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentImagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttachmentImages in the database
        List<AttachmentImages> attachmentImagesList = attachmentImagesRepository.findAll();
        assertThat(attachmentImagesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AttachmentImages in Elasticsearch
        verify(mockAttachmentImagesSearchRepository, times(0)).save(attachmentImages);
    }

    @Test
    public void deleteAttachmentImages() throws Exception {
        // Initialize the database
        attachmentImagesRepository.save(attachmentImages);

        int databaseSizeBeforeDelete = attachmentImagesRepository.findAll().size();

        // Delete the attachmentImages
        restAttachmentImagesMockMvc.perform(delete("/api/attachment-images/{id}", attachmentImages.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttachmentImages> attachmentImagesList = attachmentImagesRepository.findAll();
        assertThat(attachmentImagesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AttachmentImages in Elasticsearch
        verify(mockAttachmentImagesSearchRepository, times(1)).deleteById(attachmentImages.getId());
    }

    @Test
    public void searchAttachmentImages() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        attachmentImagesRepository.save(attachmentImages);
        when(mockAttachmentImagesSearchRepository.search(queryStringQuery("id:" + attachmentImages.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(attachmentImages), PageRequest.of(0, 1), 1));

        // Search the attachmentImages
        restAttachmentImagesMockMvc.perform(get("/api/_search/attachment-images?query=id:" + attachmentImages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachmentImages.getId())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
