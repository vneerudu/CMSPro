package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.Stamps;
import com.cmspro.base.microservice.repository.StampsRepository;
import com.cmspro.base.microservice.repository.search.StampsSearchRepository;
import com.cmspro.base.microservice.service.StampsService;
import com.cmspro.base.microservice.service.dto.StampsDTO;
import com.cmspro.base.microservice.service.mapper.StampsMapper;

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
 * Integration tests for the {@link StampsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class StampsResourceIT {

    private static final String DEFAULT_STAMP = "AAAAAAAAAA";
    private static final String UPDATED_STAMP = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StampsRepository stampsRepository;

    @Autowired
    private StampsMapper stampsMapper;

    @Autowired
    private StampsService stampsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.StampsSearchRepositoryMockConfiguration
     */
    @Autowired
    private StampsSearchRepository mockStampsSearchRepository;

    @Autowired
    private MockMvc restStampsMockMvc;

    private Stamps stamps;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stamps createEntity() {
        Stamps stamps = new Stamps()
            .stamp(DEFAULT_STAMP)
            .title(DEFAULT_TITLE)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return stamps;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stamps createUpdatedEntity() {
        Stamps stamps = new Stamps()
            .stamp(UPDATED_STAMP)
            .title(UPDATED_TITLE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return stamps;
    }

    @BeforeEach
    public void initTest() {
        stampsRepository.deleteAll();
        stamps = createEntity();
    }

    @Test
    public void createStamps() throws Exception {
        int databaseSizeBeforeCreate = stampsRepository.findAll().size();
        // Create the Stamps
        StampsDTO stampsDTO = stampsMapper.toDto(stamps);
        restStampsMockMvc.perform(post("/api/stamps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stampsDTO)))
            .andExpect(status().isCreated());

        // Validate the Stamps in the database
        List<Stamps> stampsList = stampsRepository.findAll();
        assertThat(stampsList).hasSize(databaseSizeBeforeCreate + 1);
        Stamps testStamps = stampsList.get(stampsList.size() - 1);
        assertThat(testStamps.getStamp()).isEqualTo(DEFAULT_STAMP);
        assertThat(testStamps.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testStamps.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testStamps.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the Stamps in Elasticsearch
        verify(mockStampsSearchRepository, times(1)).save(testStamps);
    }

    @Test
    public void createStampsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stampsRepository.findAll().size();

        // Create the Stamps with an existing ID
        stamps.setId("existing_id");
        StampsDTO stampsDTO = stampsMapper.toDto(stamps);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStampsMockMvc.perform(post("/api/stamps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stampsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stamps in the database
        List<Stamps> stampsList = stampsRepository.findAll();
        assertThat(stampsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Stamps in Elasticsearch
        verify(mockStampsSearchRepository, times(0)).save(stamps);
    }


    @Test
    public void checkStampIsRequired() throws Exception {
        int databaseSizeBeforeTest = stampsRepository.findAll().size();
        // set the field null
        stamps.setStamp(null);

        // Create the Stamps, which fails.
        StampsDTO stampsDTO = stampsMapper.toDto(stamps);


        restStampsMockMvc.perform(post("/api/stamps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stampsDTO)))
            .andExpect(status().isBadRequest());

        List<Stamps> stampsList = stampsRepository.findAll();
        assertThat(stampsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = stampsRepository.findAll().size();
        // set the field null
        stamps.setTitle(null);

        // Create the Stamps, which fails.
        StampsDTO stampsDTO = stampsMapper.toDto(stamps);


        restStampsMockMvc.perform(post("/api/stamps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stampsDTO)))
            .andExpect(status().isBadRequest());

        List<Stamps> stampsList = stampsRepository.findAll();
        assertThat(stampsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = stampsRepository.findAll().size();
        // set the field null
        stamps.setCreatedBy(null);

        // Create the Stamps, which fails.
        StampsDTO stampsDTO = stampsMapper.toDto(stamps);


        restStampsMockMvc.perform(post("/api/stamps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stampsDTO)))
            .andExpect(status().isBadRequest());

        List<Stamps> stampsList = stampsRepository.findAll();
        assertThat(stampsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stampsRepository.findAll().size();
        // set the field null
        stamps.setCreationDate(null);

        // Create the Stamps, which fails.
        StampsDTO stampsDTO = stampsMapper.toDto(stamps);


        restStampsMockMvc.perform(post("/api/stamps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stampsDTO)))
            .andExpect(status().isBadRequest());

        List<Stamps> stampsList = stampsRepository.findAll();
        assertThat(stampsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllStamps() throws Exception {
        // Initialize the database
        stampsRepository.save(stamps);

        // Get all the stampsList
        restStampsMockMvc.perform(get("/api/stamps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stamps.getId())))
            .andExpect(jsonPath("$.[*].stamp").value(hasItem(DEFAULT_STAMP)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getStamps() throws Exception {
        // Initialize the database
        stampsRepository.save(stamps);

        // Get the stamps
        restStampsMockMvc.perform(get("/api/stamps/{id}", stamps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stamps.getId()))
            .andExpect(jsonPath("$.stamp").value(DEFAULT_STAMP))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingStamps() throws Exception {
        // Get the stamps
        restStampsMockMvc.perform(get("/api/stamps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateStamps() throws Exception {
        // Initialize the database
        stampsRepository.save(stamps);

        int databaseSizeBeforeUpdate = stampsRepository.findAll().size();

        // Update the stamps
        Stamps updatedStamps = stampsRepository.findById(stamps.getId()).get();
        updatedStamps
            .stamp(UPDATED_STAMP)
            .title(UPDATED_TITLE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        StampsDTO stampsDTO = stampsMapper.toDto(updatedStamps);

        restStampsMockMvc.perform(put("/api/stamps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stampsDTO)))
            .andExpect(status().isOk());

        // Validate the Stamps in the database
        List<Stamps> stampsList = stampsRepository.findAll();
        assertThat(stampsList).hasSize(databaseSizeBeforeUpdate);
        Stamps testStamps = stampsList.get(stampsList.size() - 1);
        assertThat(testStamps.getStamp()).isEqualTo(UPDATED_STAMP);
        assertThat(testStamps.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testStamps.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStamps.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the Stamps in Elasticsearch
        verify(mockStampsSearchRepository, times(1)).save(testStamps);
    }

    @Test
    public void updateNonExistingStamps() throws Exception {
        int databaseSizeBeforeUpdate = stampsRepository.findAll().size();

        // Create the Stamps
        StampsDTO stampsDTO = stampsMapper.toDto(stamps);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStampsMockMvc.perform(put("/api/stamps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stampsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stamps in the database
        List<Stamps> stampsList = stampsRepository.findAll();
        assertThat(stampsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Stamps in Elasticsearch
        verify(mockStampsSearchRepository, times(0)).save(stamps);
    }

    @Test
    public void deleteStamps() throws Exception {
        // Initialize the database
        stampsRepository.save(stamps);

        int databaseSizeBeforeDelete = stampsRepository.findAll().size();

        // Delete the stamps
        restStampsMockMvc.perform(delete("/api/stamps/{id}", stamps.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stamps> stampsList = stampsRepository.findAll();
        assertThat(stampsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Stamps in Elasticsearch
        verify(mockStampsSearchRepository, times(1)).deleteById(stamps.getId());
    }

    @Test
    public void searchStamps() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        stampsRepository.save(stamps);
        when(mockStampsSearchRepository.search(queryStringQuery("id:" + stamps.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(stamps), PageRequest.of(0, 1), 1));

        // Search the stamps
        restStampsMockMvc.perform(get("/api/_search/stamps?query=id:" + stamps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stamps.getId())))
            .andExpect(jsonPath("$.[*].stamp").value(hasItem(DEFAULT_STAMP)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
