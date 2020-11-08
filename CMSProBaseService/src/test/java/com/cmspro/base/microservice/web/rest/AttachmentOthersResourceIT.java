package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.AttachmentOthers;
import com.cmspro.base.microservice.repository.AttachmentOthersRepository;
import com.cmspro.base.microservice.repository.search.AttachmentOthersSearchRepository;
import com.cmspro.base.microservice.service.AttachmentOthersService;
import com.cmspro.base.microservice.service.dto.AttachmentOthersDTO;
import com.cmspro.base.microservice.service.mapper.AttachmentOthersMapper;

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
 * Integration tests for the {@link AttachmentOthersResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttachmentOthersResourceIT {

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
    private AttachmentOthersRepository attachmentOthersRepository;

    @Autowired
    private AttachmentOthersMapper attachmentOthersMapper;

    @Autowired
    private AttachmentOthersService attachmentOthersService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.AttachmentOthersSearchRepositoryMockConfiguration
     */
    @Autowired
    private AttachmentOthersSearchRepository mockAttachmentOthersSearchRepository;

    @Autowired
    private MockMvc restAttachmentOthersMockMvc;

    private AttachmentOthers attachmentOthers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachmentOthers createEntity() {
        AttachmentOthers attachmentOthers = new AttachmentOthers()
            .fileName(DEFAULT_FILE_NAME)
            .fileType(DEFAULT_FILE_TYPE)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return attachmentOthers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachmentOthers createUpdatedEntity() {
        AttachmentOthers attachmentOthers = new AttachmentOthers()
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return attachmentOthers;
    }

    @BeforeEach
    public void initTest() {
        attachmentOthersRepository.deleteAll();
        attachmentOthers = createEntity();
    }

    @Test
    public void createAttachmentOthers() throws Exception {
        int databaseSizeBeforeCreate = attachmentOthersRepository.findAll().size();
        // Create the AttachmentOthers
        AttachmentOthersDTO attachmentOthersDTO = attachmentOthersMapper.toDto(attachmentOthers);
        restAttachmentOthersMockMvc.perform(post("/api/attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentOthersDTO)))
            .andExpect(status().isCreated());

        // Validate the AttachmentOthers in the database
        List<AttachmentOthers> attachmentOthersList = attachmentOthersRepository.findAll();
        assertThat(attachmentOthersList).hasSize(databaseSizeBeforeCreate + 1);
        AttachmentOthers testAttachmentOthers = attachmentOthersList.get(attachmentOthersList.size() - 1);
        assertThat(testAttachmentOthers.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testAttachmentOthers.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testAttachmentOthers.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testAttachmentOthers.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testAttachmentOthers.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAttachmentOthers.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the AttachmentOthers in Elasticsearch
        verify(mockAttachmentOthersSearchRepository, times(1)).save(testAttachmentOthers);
    }

    @Test
    public void createAttachmentOthersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attachmentOthersRepository.findAll().size();

        // Create the AttachmentOthers with an existing ID
        attachmentOthers.setId("existing_id");
        AttachmentOthersDTO attachmentOthersDTO = attachmentOthersMapper.toDto(attachmentOthers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachmentOthersMockMvc.perform(post("/api/attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentOthersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttachmentOthers in the database
        List<AttachmentOthers> attachmentOthersList = attachmentOthersRepository.findAll();
        assertThat(attachmentOthersList).hasSize(databaseSizeBeforeCreate);

        // Validate the AttachmentOthers in Elasticsearch
        verify(mockAttachmentOthersSearchRepository, times(0)).save(attachmentOthers);
    }


    @Test
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentOthersRepository.findAll().size();
        // set the field null
        attachmentOthers.setFileName(null);

        // Create the AttachmentOthers, which fails.
        AttachmentOthersDTO attachmentOthersDTO = attachmentOthersMapper.toDto(attachmentOthers);


        restAttachmentOthersMockMvc.perform(post("/api/attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentOthersDTO)))
            .andExpect(status().isBadRequest());

        List<AttachmentOthers> attachmentOthersList = attachmentOthersRepository.findAll();
        assertThat(attachmentOthersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentOthersRepository.findAll().size();
        // set the field null
        attachmentOthers.setCreatedBy(null);

        // Create the AttachmentOthers, which fails.
        AttachmentOthersDTO attachmentOthersDTO = attachmentOthersMapper.toDto(attachmentOthers);


        restAttachmentOthersMockMvc.perform(post("/api/attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentOthersDTO)))
            .andExpect(status().isBadRequest());

        List<AttachmentOthers> attachmentOthersList = attachmentOthersRepository.findAll();
        assertThat(attachmentOthersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentOthersRepository.findAll().size();
        // set the field null
        attachmentOthers.setCreationDate(null);

        // Create the AttachmentOthers, which fails.
        AttachmentOthersDTO attachmentOthersDTO = attachmentOthersMapper.toDto(attachmentOthers);


        restAttachmentOthersMockMvc.perform(post("/api/attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentOthersDTO)))
            .andExpect(status().isBadRequest());

        List<AttachmentOthers> attachmentOthersList = attachmentOthersRepository.findAll();
        assertThat(attachmentOthersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAttachmentOthers() throws Exception {
        // Initialize the database
        attachmentOthersRepository.save(attachmentOthers);

        // Get all the attachmentOthersList
        restAttachmentOthersMockMvc.perform(get("/api/attachment-others?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachmentOthers.getId())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getAttachmentOthers() throws Exception {
        // Initialize the database
        attachmentOthersRepository.save(attachmentOthers);

        // Get the attachmentOthers
        restAttachmentOthersMockMvc.perform(get("/api/attachment-others/{id}", attachmentOthers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attachmentOthers.getId()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingAttachmentOthers() throws Exception {
        // Get the attachmentOthers
        restAttachmentOthersMockMvc.perform(get("/api/attachment-others/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAttachmentOthers() throws Exception {
        // Initialize the database
        attachmentOthersRepository.save(attachmentOthers);

        int databaseSizeBeforeUpdate = attachmentOthersRepository.findAll().size();

        // Update the attachmentOthers
        AttachmentOthers updatedAttachmentOthers = attachmentOthersRepository.findById(attachmentOthers.getId()).get();
        updatedAttachmentOthers
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        AttachmentOthersDTO attachmentOthersDTO = attachmentOthersMapper.toDto(updatedAttachmentOthers);

        restAttachmentOthersMockMvc.perform(put("/api/attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentOthersDTO)))
            .andExpect(status().isOk());

        // Validate the AttachmentOthers in the database
        List<AttachmentOthers> attachmentOthersList = attachmentOthersRepository.findAll();
        assertThat(attachmentOthersList).hasSize(databaseSizeBeforeUpdate);
        AttachmentOthers testAttachmentOthers = attachmentOthersList.get(attachmentOthersList.size() - 1);
        assertThat(testAttachmentOthers.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testAttachmentOthers.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testAttachmentOthers.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAttachmentOthers.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testAttachmentOthers.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttachmentOthers.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the AttachmentOthers in Elasticsearch
        verify(mockAttachmentOthersSearchRepository, times(1)).save(testAttachmentOthers);
    }

    @Test
    public void updateNonExistingAttachmentOthers() throws Exception {
        int databaseSizeBeforeUpdate = attachmentOthersRepository.findAll().size();

        // Create the AttachmentOthers
        AttachmentOthersDTO attachmentOthersDTO = attachmentOthersMapper.toDto(attachmentOthers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentOthersMockMvc.perform(put("/api/attachment-others")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentOthersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttachmentOthers in the database
        List<AttachmentOthers> attachmentOthersList = attachmentOthersRepository.findAll();
        assertThat(attachmentOthersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AttachmentOthers in Elasticsearch
        verify(mockAttachmentOthersSearchRepository, times(0)).save(attachmentOthers);
    }

    @Test
    public void deleteAttachmentOthers() throws Exception {
        // Initialize the database
        attachmentOthersRepository.save(attachmentOthers);

        int databaseSizeBeforeDelete = attachmentOthersRepository.findAll().size();

        // Delete the attachmentOthers
        restAttachmentOthersMockMvc.perform(delete("/api/attachment-others/{id}", attachmentOthers.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttachmentOthers> attachmentOthersList = attachmentOthersRepository.findAll();
        assertThat(attachmentOthersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AttachmentOthers in Elasticsearch
        verify(mockAttachmentOthersSearchRepository, times(1)).deleteById(attachmentOthers.getId());
    }

    @Test
    public void searchAttachmentOthers() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        attachmentOthersRepository.save(attachmentOthers);
        when(mockAttachmentOthersSearchRepository.search(queryStringQuery("id:" + attachmentOthers.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(attachmentOthers), PageRequest.of(0, 1), 1));

        // Search the attachmentOthers
        restAttachmentOthersMockMvc.perform(get("/api/_search/attachment-others?query=id:" + attachmentOthers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachmentOthers.getId())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
