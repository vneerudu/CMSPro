package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.RFITimeLine;
import com.cmspro.base.microservice.repository.RFITimeLineRepository;
import com.cmspro.base.microservice.repository.search.RFITimeLineSearchRepository;
import com.cmspro.base.microservice.service.RFITimeLineService;
import com.cmspro.base.microservice.service.dto.RFITimeLineDTO;
import com.cmspro.base.microservice.service.mapper.RFITimeLineMapper;

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
 * Integration tests for the {@link RFITimeLineResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RFITimeLineResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RFITimeLineRepository rFITimeLineRepository;

    @Autowired
    private RFITimeLineMapper rFITimeLineMapper;

    @Autowired
    private RFITimeLineService rFITimeLineService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.RFITimeLineSearchRepositoryMockConfiguration
     */
    @Autowired
    private RFITimeLineSearchRepository mockRFITimeLineSearchRepository;

    @Autowired
    private MockMvc restRFITimeLineMockMvc;

    private RFITimeLine rFITimeLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFITimeLine createEntity() {
        RFITimeLine rFITimeLine = new RFITimeLine()
            .createdBy(DEFAULT_CREATED_BY)
            .message(DEFAULT_MESSAGE)
            .creationDate(DEFAULT_CREATION_DATE);
        return rFITimeLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFITimeLine createUpdatedEntity() {
        RFITimeLine rFITimeLine = new RFITimeLine()
            .createdBy(UPDATED_CREATED_BY)
            .message(UPDATED_MESSAGE)
            .creationDate(UPDATED_CREATION_DATE);
        return rFITimeLine;
    }

    @BeforeEach
    public void initTest() {
        rFITimeLineRepository.deleteAll();
        rFITimeLine = createEntity();
    }

    @Test
    public void createRFITimeLine() throws Exception {
        int databaseSizeBeforeCreate = rFITimeLineRepository.findAll().size();
        // Create the RFITimeLine
        RFITimeLineDTO rFITimeLineDTO = rFITimeLineMapper.toDto(rFITimeLine);
        restRFITimeLineMockMvc.perform(post("/api/rfi-time-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFITimeLineDTO)))
            .andExpect(status().isCreated());

        // Validate the RFITimeLine in the database
        List<RFITimeLine> rFITimeLineList = rFITimeLineRepository.findAll();
        assertThat(rFITimeLineList).hasSize(databaseSizeBeforeCreate + 1);
        RFITimeLine testRFITimeLine = rFITimeLineList.get(rFITimeLineList.size() - 1);
        assertThat(testRFITimeLine.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRFITimeLine.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testRFITimeLine.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the RFITimeLine in Elasticsearch
        verify(mockRFITimeLineSearchRepository, times(1)).save(testRFITimeLine);
    }

    @Test
    public void createRFITimeLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rFITimeLineRepository.findAll().size();

        // Create the RFITimeLine with an existing ID
        rFITimeLine.setId("existing_id");
        RFITimeLineDTO rFITimeLineDTO = rFITimeLineMapper.toDto(rFITimeLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFITimeLineMockMvc.perform(post("/api/rfi-time-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFITimeLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RFITimeLine in the database
        List<RFITimeLine> rFITimeLineList = rFITimeLineRepository.findAll();
        assertThat(rFITimeLineList).hasSize(databaseSizeBeforeCreate);

        // Validate the RFITimeLine in Elasticsearch
        verify(mockRFITimeLineSearchRepository, times(0)).save(rFITimeLine);
    }


    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = rFITimeLineRepository.findAll().size();
        // set the field null
        rFITimeLine.setCreatedBy(null);

        // Create the RFITimeLine, which fails.
        RFITimeLineDTO rFITimeLineDTO = rFITimeLineMapper.toDto(rFITimeLine);


        restRFITimeLineMockMvc.perform(post("/api/rfi-time-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFITimeLineDTO)))
            .andExpect(status().isBadRequest());

        List<RFITimeLine> rFITimeLineList = rFITimeLineRepository.findAll();
        assertThat(rFITimeLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = rFITimeLineRepository.findAll().size();
        // set the field null
        rFITimeLine.setMessage(null);

        // Create the RFITimeLine, which fails.
        RFITimeLineDTO rFITimeLineDTO = rFITimeLineMapper.toDto(rFITimeLine);


        restRFITimeLineMockMvc.perform(post("/api/rfi-time-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFITimeLineDTO)))
            .andExpect(status().isBadRequest());

        List<RFITimeLine> rFITimeLineList = rFITimeLineRepository.findAll();
        assertThat(rFITimeLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = rFITimeLineRepository.findAll().size();
        // set the field null
        rFITimeLine.setCreationDate(null);

        // Create the RFITimeLine, which fails.
        RFITimeLineDTO rFITimeLineDTO = rFITimeLineMapper.toDto(rFITimeLine);


        restRFITimeLineMockMvc.perform(post("/api/rfi-time-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFITimeLineDTO)))
            .andExpect(status().isBadRequest());

        List<RFITimeLine> rFITimeLineList = rFITimeLineRepository.findAll();
        assertThat(rFITimeLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRFITimeLines() throws Exception {
        // Initialize the database
        rFITimeLineRepository.save(rFITimeLine);

        // Get all the rFITimeLineList
        restRFITimeLineMockMvc.perform(get("/api/rfi-time-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFITimeLine.getId())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getRFITimeLine() throws Exception {
        // Initialize the database
        rFITimeLineRepository.save(rFITimeLine);

        // Get the rFITimeLine
        restRFITimeLineMockMvc.perform(get("/api/rfi-time-lines/{id}", rFITimeLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFITimeLine.getId()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingRFITimeLine() throws Exception {
        // Get the rFITimeLine
        restRFITimeLineMockMvc.perform(get("/api/rfi-time-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRFITimeLine() throws Exception {
        // Initialize the database
        rFITimeLineRepository.save(rFITimeLine);

        int databaseSizeBeforeUpdate = rFITimeLineRepository.findAll().size();

        // Update the rFITimeLine
        RFITimeLine updatedRFITimeLine = rFITimeLineRepository.findById(rFITimeLine.getId()).get();
        updatedRFITimeLine
            .createdBy(UPDATED_CREATED_BY)
            .message(UPDATED_MESSAGE)
            .creationDate(UPDATED_CREATION_DATE);
        RFITimeLineDTO rFITimeLineDTO = rFITimeLineMapper.toDto(updatedRFITimeLine);

        restRFITimeLineMockMvc.perform(put("/api/rfi-time-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFITimeLineDTO)))
            .andExpect(status().isOk());

        // Validate the RFITimeLine in the database
        List<RFITimeLine> rFITimeLineList = rFITimeLineRepository.findAll();
        assertThat(rFITimeLineList).hasSize(databaseSizeBeforeUpdate);
        RFITimeLine testRFITimeLine = rFITimeLineList.get(rFITimeLineList.size() - 1);
        assertThat(testRFITimeLine.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRFITimeLine.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testRFITimeLine.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the RFITimeLine in Elasticsearch
        verify(mockRFITimeLineSearchRepository, times(1)).save(testRFITimeLine);
    }

    @Test
    public void updateNonExistingRFITimeLine() throws Exception {
        int databaseSizeBeforeUpdate = rFITimeLineRepository.findAll().size();

        // Create the RFITimeLine
        RFITimeLineDTO rFITimeLineDTO = rFITimeLineMapper.toDto(rFITimeLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFITimeLineMockMvc.perform(put("/api/rfi-time-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFITimeLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RFITimeLine in the database
        List<RFITimeLine> rFITimeLineList = rFITimeLineRepository.findAll();
        assertThat(rFITimeLineList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RFITimeLine in Elasticsearch
        verify(mockRFITimeLineSearchRepository, times(0)).save(rFITimeLine);
    }

    @Test
    public void deleteRFITimeLine() throws Exception {
        // Initialize the database
        rFITimeLineRepository.save(rFITimeLine);

        int databaseSizeBeforeDelete = rFITimeLineRepository.findAll().size();

        // Delete the rFITimeLine
        restRFITimeLineMockMvc.perform(delete("/api/rfi-time-lines/{id}", rFITimeLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFITimeLine> rFITimeLineList = rFITimeLineRepository.findAll();
        assertThat(rFITimeLineList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RFITimeLine in Elasticsearch
        verify(mockRFITimeLineSearchRepository, times(1)).deleteById(rFITimeLine.getId());
    }

    @Test
    public void searchRFITimeLine() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        rFITimeLineRepository.save(rFITimeLine);
        when(mockRFITimeLineSearchRepository.search(queryStringQuery("id:" + rFITimeLine.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(rFITimeLine), PageRequest.of(0, 1), 1));

        // Search the rFITimeLine
        restRFITimeLineMockMvc.perform(get("/api/_search/rfi-time-lines?query=id:" + rFITimeLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFITimeLine.getId())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
