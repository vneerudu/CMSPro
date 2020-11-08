package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.Logos;
import com.cmspro.base.microservice.repository.LogosRepository;
import com.cmspro.base.microservice.repository.search.LogosSearchRepository;
import com.cmspro.base.microservice.service.LogosService;
import com.cmspro.base.microservice.service.dto.LogosDTO;
import com.cmspro.base.microservice.service.mapper.LogosMapper;

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
 * Integration tests for the {@link LogosResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LogosResourceIT {

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
    private LogosRepository logosRepository;

    @Autowired
    private LogosMapper logosMapper;

    @Autowired
    private LogosService logosService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.LogosSearchRepositoryMockConfiguration
     */
    @Autowired
    private LogosSearchRepository mockLogosSearchRepository;

    @Autowired
    private MockMvc restLogosMockMvc;

    private Logos logos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Logos createEntity() {
        Logos logos = new Logos()
            .fileName(DEFAULT_FILE_NAME)
            .fileType(DEFAULT_FILE_TYPE)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return logos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Logos createUpdatedEntity() {
        Logos logos = new Logos()
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return logos;
    }

    @BeforeEach
    public void initTest() {
        logosRepository.deleteAll();
        logos = createEntity();
    }

    @Test
    public void createLogos() throws Exception {
        int databaseSizeBeforeCreate = logosRepository.findAll().size();
        // Create the Logos
        LogosDTO logosDTO = logosMapper.toDto(logos);
        restLogosMockMvc.perform(post("/api/logos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logosDTO)))
            .andExpect(status().isCreated());

        // Validate the Logos in the database
        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeCreate + 1);
        Logos testLogos = logosList.get(logosList.size() - 1);
        assertThat(testLogos.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testLogos.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testLogos.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testLogos.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testLogos.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLogos.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the Logos in Elasticsearch
        verify(mockLogosSearchRepository, times(1)).save(testLogos);
    }

    @Test
    public void createLogosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logosRepository.findAll().size();

        // Create the Logos with an existing ID
        logos.setId("existing_id");
        LogosDTO logosDTO = logosMapper.toDto(logos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogosMockMvc.perform(post("/api/logos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Logos in the database
        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeCreate);

        // Validate the Logos in Elasticsearch
        verify(mockLogosSearchRepository, times(0)).save(logos);
    }


    @Test
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = logosRepository.findAll().size();
        // set the field null
        logos.setFileName(null);

        // Create the Logos, which fails.
        LogosDTO logosDTO = logosMapper.toDto(logos);


        restLogosMockMvc.perform(post("/api/logos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logosDTO)))
            .andExpect(status().isBadRequest());

        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = logosRepository.findAll().size();
        // set the field null
        logos.setCreatedBy(null);

        // Create the Logos, which fails.
        LogosDTO logosDTO = logosMapper.toDto(logos);


        restLogosMockMvc.perform(post("/api/logos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logosDTO)))
            .andExpect(status().isBadRequest());

        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = logosRepository.findAll().size();
        // set the field null
        logos.setCreationDate(null);

        // Create the Logos, which fails.
        LogosDTO logosDTO = logosMapper.toDto(logos);


        restLogosMockMvc.perform(post("/api/logos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logosDTO)))
            .andExpect(status().isBadRequest());

        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllLogos() throws Exception {
        // Initialize the database
        logosRepository.save(logos);

        // Get all the logosList
        restLogosMockMvc.perform(get("/api/logos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logos.getId())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getLogos() throws Exception {
        // Initialize the database
        logosRepository.save(logos);

        // Get the logos
        restLogosMockMvc.perform(get("/api/logos/{id}", logos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logos.getId()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingLogos() throws Exception {
        // Get the logos
        restLogosMockMvc.perform(get("/api/logos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateLogos() throws Exception {
        // Initialize the database
        logosRepository.save(logos);

        int databaseSizeBeforeUpdate = logosRepository.findAll().size();

        // Update the logos
        Logos updatedLogos = logosRepository.findById(logos.getId()).get();
        updatedLogos
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        LogosDTO logosDTO = logosMapper.toDto(updatedLogos);

        restLogosMockMvc.perform(put("/api/logos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logosDTO)))
            .andExpect(status().isOk());

        // Validate the Logos in the database
        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeUpdate);
        Logos testLogos = logosList.get(logosList.size() - 1);
        assertThat(testLogos.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testLogos.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testLogos.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testLogos.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testLogos.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLogos.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the Logos in Elasticsearch
        verify(mockLogosSearchRepository, times(1)).save(testLogos);
    }

    @Test
    public void updateNonExistingLogos() throws Exception {
        int databaseSizeBeforeUpdate = logosRepository.findAll().size();

        // Create the Logos
        LogosDTO logosDTO = logosMapper.toDto(logos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogosMockMvc.perform(put("/api/logos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Logos in the database
        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Logos in Elasticsearch
        verify(mockLogosSearchRepository, times(0)).save(logos);
    }

    @Test
    public void deleteLogos() throws Exception {
        // Initialize the database
        logosRepository.save(logos);

        int databaseSizeBeforeDelete = logosRepository.findAll().size();

        // Delete the logos
        restLogosMockMvc.perform(delete("/api/logos/{id}", logos.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Logos in Elasticsearch
        verify(mockLogosSearchRepository, times(1)).deleteById(logos.getId());
    }

    @Test
    public void searchLogos() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        logosRepository.save(logos);
        when(mockLogosSearchRepository.search(queryStringQuery("id:" + logos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(logos), PageRequest.of(0, 1), 1));

        // Search the logos
        restLogosMockMvc.perform(get("/api/_search/logos?query=id:" + logos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logos.getId())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
