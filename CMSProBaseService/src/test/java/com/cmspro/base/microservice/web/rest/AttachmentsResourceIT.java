package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.Attachments;
import com.cmspro.base.microservice.repository.AttachmentsRepository;
import com.cmspro.base.microservice.repository.search.AttachmentsSearchRepository;
import com.cmspro.base.microservice.service.AttachmentsService;
import com.cmspro.base.microservice.service.dto.AttachmentsDTO;
import com.cmspro.base.microservice.service.mapper.AttachmentsMapper;

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
 * Integration tests for the {@link AttachmentsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttachmentsResourceIT {

    private static final String DEFAULT_FOLDER = "AAAAAAAAAA";
    private static final String UPDATED_FOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AttachmentsRepository attachmentsRepository;

    @Autowired
    private AttachmentsMapper attachmentsMapper;

    @Autowired
    private AttachmentsService attachmentsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.AttachmentsSearchRepositoryMockConfiguration
     */
    @Autowired
    private AttachmentsSearchRepository mockAttachmentsSearchRepository;

    @Autowired
    private MockMvc restAttachmentsMockMvc;

    private Attachments attachments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attachments createEntity() {
        Attachments attachments = new Attachments()
            .folder(DEFAULT_FOLDER)
            .fileName(DEFAULT_FILE_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return attachments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attachments createUpdatedEntity() {
        Attachments attachments = new Attachments()
            .folder(UPDATED_FOLDER)
            .fileName(UPDATED_FILE_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return attachments;
    }

    @BeforeEach
    public void initTest() {
        attachmentsRepository.deleteAll();
        attachments = createEntity();
    }

    @Test
    public void createAttachments() throws Exception {
        int databaseSizeBeforeCreate = attachmentsRepository.findAll().size();
        // Create the Attachments
        AttachmentsDTO attachmentsDTO = attachmentsMapper.toDto(attachments);
        restAttachmentsMockMvc.perform(post("/api/attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Attachments in the database
        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeCreate + 1);
        Attachments testAttachments = attachmentsList.get(attachmentsList.size() - 1);
        assertThat(testAttachments.getFolder()).isEqualTo(DEFAULT_FOLDER);
        assertThat(testAttachments.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testAttachments.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAttachments.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the Attachments in Elasticsearch
        verify(mockAttachmentsSearchRepository, times(1)).save(testAttachments);
    }

    @Test
    public void createAttachmentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attachmentsRepository.findAll().size();

        // Create the Attachments with an existing ID
        attachments.setId("existing_id");
        AttachmentsDTO attachmentsDTO = attachmentsMapper.toDto(attachments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachmentsMockMvc.perform(post("/api/attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attachments in the database
        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Attachments in Elasticsearch
        verify(mockAttachmentsSearchRepository, times(0)).save(attachments);
    }


    @Test
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentsRepository.findAll().size();
        // set the field null
        attachments.setFileName(null);

        // Create the Attachments, which fails.
        AttachmentsDTO attachmentsDTO = attachmentsMapper.toDto(attachments);


        restAttachmentsMockMvc.perform(post("/api/attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsDTO)))
            .andExpect(status().isBadRequest());

        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentsRepository.findAll().size();
        // set the field null
        attachments.setCreatedBy(null);

        // Create the Attachments, which fails.
        AttachmentsDTO attachmentsDTO = attachmentsMapper.toDto(attachments);


        restAttachmentsMockMvc.perform(post("/api/attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsDTO)))
            .andExpect(status().isBadRequest());

        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = attachmentsRepository.findAll().size();
        // set the field null
        attachments.setCreationDate(null);

        // Create the Attachments, which fails.
        AttachmentsDTO attachmentsDTO = attachmentsMapper.toDto(attachments);


        restAttachmentsMockMvc.perform(post("/api/attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsDTO)))
            .andExpect(status().isBadRequest());

        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAttachments() throws Exception {
        // Initialize the database
        attachmentsRepository.save(attachments);

        // Get all the attachmentsList
        restAttachmentsMockMvc.perform(get("/api/attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachments.getId())))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getAttachments() throws Exception {
        // Initialize the database
        attachmentsRepository.save(attachments);

        // Get the attachments
        restAttachmentsMockMvc.perform(get("/api/attachments/{id}", attachments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attachments.getId()))
            .andExpect(jsonPath("$.folder").value(DEFAULT_FOLDER))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingAttachments() throws Exception {
        // Get the attachments
        restAttachmentsMockMvc.perform(get("/api/attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAttachments() throws Exception {
        // Initialize the database
        attachmentsRepository.save(attachments);

        int databaseSizeBeforeUpdate = attachmentsRepository.findAll().size();

        // Update the attachments
        Attachments updatedAttachments = attachmentsRepository.findById(attachments.getId()).get();
        updatedAttachments
            .folder(UPDATED_FOLDER)
            .fileName(UPDATED_FILE_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        AttachmentsDTO attachmentsDTO = attachmentsMapper.toDto(updatedAttachments);

        restAttachmentsMockMvc.perform(put("/api/attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsDTO)))
            .andExpect(status().isOk());

        // Validate the Attachments in the database
        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeUpdate);
        Attachments testAttachments = attachmentsList.get(attachmentsList.size() - 1);
        assertThat(testAttachments.getFolder()).isEqualTo(UPDATED_FOLDER);
        assertThat(testAttachments.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testAttachments.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttachments.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the Attachments in Elasticsearch
        verify(mockAttachmentsSearchRepository, times(1)).save(testAttachments);
    }

    @Test
    public void updateNonExistingAttachments() throws Exception {
        int databaseSizeBeforeUpdate = attachmentsRepository.findAll().size();

        // Create the Attachments
        AttachmentsDTO attachmentsDTO = attachmentsMapper.toDto(attachments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentsMockMvc.perform(put("/api/attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachmentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attachments in the database
        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Attachments in Elasticsearch
        verify(mockAttachmentsSearchRepository, times(0)).save(attachments);
    }

    @Test
    public void deleteAttachments() throws Exception {
        // Initialize the database
        attachmentsRepository.save(attachments);

        int databaseSizeBeforeDelete = attachmentsRepository.findAll().size();

        // Delete the attachments
        restAttachmentsMockMvc.perform(delete("/api/attachments/{id}", attachments.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attachments> attachmentsList = attachmentsRepository.findAll();
        assertThat(attachmentsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Attachments in Elasticsearch
        verify(mockAttachmentsSearchRepository, times(1)).deleteById(attachments.getId());
    }

    @Test
    public void searchAttachments() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        attachmentsRepository.save(attachments);
        when(mockAttachmentsSearchRepository.search(queryStringQuery("id:" + attachments.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(attachments), PageRequest.of(0, 1), 1));

        // Search the attachments
        restAttachmentsMockMvc.perform(get("/api/_search/attachments?query=id:" + attachments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachments.getId())))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
