package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.RFIComments;
import com.cmspro.base.microservice.repository.RFICommentsRepository;
import com.cmspro.base.microservice.repository.search.RFICommentsSearchRepository;
import com.cmspro.base.microservice.service.RFICommentsService;
import com.cmspro.base.microservice.service.dto.RFICommentsDTO;
import com.cmspro.base.microservice.service.mapper.RFICommentsMapper;

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
 * Integration tests for the {@link RFICommentsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RFICommentsResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RFICommentsRepository rFICommentsRepository;

    @Autowired
    private RFICommentsMapper rFICommentsMapper;

    @Autowired
    private RFICommentsService rFICommentsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.RFICommentsSearchRepositoryMockConfiguration
     */
    @Autowired
    private RFICommentsSearchRepository mockRFICommentsSearchRepository;

    @Autowired
    private MockMvc restRFICommentsMockMvc;

    private RFIComments rFIComments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFIComments createEntity() {
        RFIComments rFIComments = new RFIComments()
            .createdBy(DEFAULT_CREATED_BY)
            .comment(DEFAULT_COMMENT)
            .creationDate(DEFAULT_CREATION_DATE);
        return rFIComments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFIComments createUpdatedEntity() {
        RFIComments rFIComments = new RFIComments()
            .createdBy(UPDATED_CREATED_BY)
            .comment(UPDATED_COMMENT)
            .creationDate(UPDATED_CREATION_DATE);
        return rFIComments;
    }

    @BeforeEach
    public void initTest() {
        rFICommentsRepository.deleteAll();
        rFIComments = createEntity();
    }

    @Test
    public void createRFIComments() throws Exception {
        int databaseSizeBeforeCreate = rFICommentsRepository.findAll().size();
        // Create the RFIComments
        RFICommentsDTO rFICommentsDTO = rFICommentsMapper.toDto(rFIComments);
        restRFICommentsMockMvc.perform(post("/api/rfi-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFICommentsDTO)))
            .andExpect(status().isCreated());

        // Validate the RFIComments in the database
        List<RFIComments> rFICommentsList = rFICommentsRepository.findAll();
        assertThat(rFICommentsList).hasSize(databaseSizeBeforeCreate + 1);
        RFIComments testRFIComments = rFICommentsList.get(rFICommentsList.size() - 1);
        assertThat(testRFIComments.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRFIComments.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testRFIComments.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the RFIComments in Elasticsearch
        verify(mockRFICommentsSearchRepository, times(1)).save(testRFIComments);
    }

    @Test
    public void createRFICommentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rFICommentsRepository.findAll().size();

        // Create the RFIComments with an existing ID
        rFIComments.setId("existing_id");
        RFICommentsDTO rFICommentsDTO = rFICommentsMapper.toDto(rFIComments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFICommentsMockMvc.perform(post("/api/rfi-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFICommentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RFIComments in the database
        List<RFIComments> rFICommentsList = rFICommentsRepository.findAll();
        assertThat(rFICommentsList).hasSize(databaseSizeBeforeCreate);

        // Validate the RFIComments in Elasticsearch
        verify(mockRFICommentsSearchRepository, times(0)).save(rFIComments);
    }


    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = rFICommentsRepository.findAll().size();
        // set the field null
        rFIComments.setCreatedBy(null);

        // Create the RFIComments, which fails.
        RFICommentsDTO rFICommentsDTO = rFICommentsMapper.toDto(rFIComments);


        restRFICommentsMockMvc.perform(post("/api/rfi-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFICommentsDTO)))
            .andExpect(status().isBadRequest());

        List<RFIComments> rFICommentsList = rFICommentsRepository.findAll();
        assertThat(rFICommentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = rFICommentsRepository.findAll().size();
        // set the field null
        rFIComments.setComment(null);

        // Create the RFIComments, which fails.
        RFICommentsDTO rFICommentsDTO = rFICommentsMapper.toDto(rFIComments);


        restRFICommentsMockMvc.perform(post("/api/rfi-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFICommentsDTO)))
            .andExpect(status().isBadRequest());

        List<RFIComments> rFICommentsList = rFICommentsRepository.findAll();
        assertThat(rFICommentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = rFICommentsRepository.findAll().size();
        // set the field null
        rFIComments.setCreationDate(null);

        // Create the RFIComments, which fails.
        RFICommentsDTO rFICommentsDTO = rFICommentsMapper.toDto(rFIComments);


        restRFICommentsMockMvc.perform(post("/api/rfi-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFICommentsDTO)))
            .andExpect(status().isBadRequest());

        List<RFIComments> rFICommentsList = rFICommentsRepository.findAll();
        assertThat(rFICommentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRFIComments() throws Exception {
        // Initialize the database
        rFICommentsRepository.save(rFIComments);

        // Get all the rFICommentsList
        restRFICommentsMockMvc.perform(get("/api/rfi-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFIComments.getId())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getRFIComments() throws Exception {
        // Initialize the database
        rFICommentsRepository.save(rFIComments);

        // Get the rFIComments
        restRFICommentsMockMvc.perform(get("/api/rfi-comments/{id}", rFIComments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFIComments.getId()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingRFIComments() throws Exception {
        // Get the rFIComments
        restRFICommentsMockMvc.perform(get("/api/rfi-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRFIComments() throws Exception {
        // Initialize the database
        rFICommentsRepository.save(rFIComments);

        int databaseSizeBeforeUpdate = rFICommentsRepository.findAll().size();

        // Update the rFIComments
        RFIComments updatedRFIComments = rFICommentsRepository.findById(rFIComments.getId()).get();
        updatedRFIComments
            .createdBy(UPDATED_CREATED_BY)
            .comment(UPDATED_COMMENT)
            .creationDate(UPDATED_CREATION_DATE);
        RFICommentsDTO rFICommentsDTO = rFICommentsMapper.toDto(updatedRFIComments);

        restRFICommentsMockMvc.perform(put("/api/rfi-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFICommentsDTO)))
            .andExpect(status().isOk());

        // Validate the RFIComments in the database
        List<RFIComments> rFICommentsList = rFICommentsRepository.findAll();
        assertThat(rFICommentsList).hasSize(databaseSizeBeforeUpdate);
        RFIComments testRFIComments = rFICommentsList.get(rFICommentsList.size() - 1);
        assertThat(testRFIComments.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRFIComments.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testRFIComments.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the RFIComments in Elasticsearch
        verify(mockRFICommentsSearchRepository, times(1)).save(testRFIComments);
    }

    @Test
    public void updateNonExistingRFIComments() throws Exception {
        int databaseSizeBeforeUpdate = rFICommentsRepository.findAll().size();

        // Create the RFIComments
        RFICommentsDTO rFICommentsDTO = rFICommentsMapper.toDto(rFIComments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFICommentsMockMvc.perform(put("/api/rfi-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFICommentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RFIComments in the database
        List<RFIComments> rFICommentsList = rFICommentsRepository.findAll();
        assertThat(rFICommentsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RFIComments in Elasticsearch
        verify(mockRFICommentsSearchRepository, times(0)).save(rFIComments);
    }

    @Test
    public void deleteRFIComments() throws Exception {
        // Initialize the database
        rFICommentsRepository.save(rFIComments);

        int databaseSizeBeforeDelete = rFICommentsRepository.findAll().size();

        // Delete the rFIComments
        restRFICommentsMockMvc.perform(delete("/api/rfi-comments/{id}", rFIComments.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFIComments> rFICommentsList = rFICommentsRepository.findAll();
        assertThat(rFICommentsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RFIComments in Elasticsearch
        verify(mockRFICommentsSearchRepository, times(1)).deleteById(rFIComments.getId());
    }

    @Test
    public void searchRFIComments() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        rFICommentsRepository.save(rFIComments);
        when(mockRFICommentsSearchRepository.search(queryStringQuery("id:" + rFIComments.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(rFIComments), PageRequest.of(0, 1), 1));

        // Search the rFIComments
        restRFICommentsMockMvc.perform(get("/api/_search/rfi-comments?query=id:" + rFIComments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFIComments.getId())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
