package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.RFI;
import com.cmspro.base.microservice.repository.RFIRepository;
import com.cmspro.base.microservice.repository.search.RFISearchRepository;
import com.cmspro.base.microservice.service.RFIService;
import com.cmspro.base.microservice.service.dto.RFIDTO;
import com.cmspro.base.microservice.service.mapper.RFIMapper;

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
 * Integration tests for the {@link RFIResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RFIResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_LOCKED = false;
    private static final Boolean UPDATED_LOCKED = true;

    private static final String DEFAULT_LOCKED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LOCKED_BY = "BBBBBBBBBB";

    @Autowired
    private RFIRepository rFIRepository;

    @Autowired
    private RFIMapper rFIMapper;

    @Autowired
    private RFIService rFIService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.RFISearchRepositoryMockConfiguration
     */
    @Autowired
    private RFISearchRepository mockRFISearchRepository;

    @Autowired
    private MockMvc restRFIMockMvc;

    private RFI rFI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFI createEntity() {
        RFI rFI = new RFI()
            .title(DEFAULT_TITLE)
            .question(DEFAULT_QUESTION)
            .answer(DEFAULT_ANSWER)
            .sentDate(DEFAULT_SENT_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .locked(DEFAULT_LOCKED)
            .lockedBy(DEFAULT_LOCKED_BY);
        return rFI;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFI createUpdatedEntity() {
        RFI rFI = new RFI()
            .title(UPDATED_TITLE)
            .question(UPDATED_QUESTION)
            .answer(UPDATED_ANSWER)
            .sentDate(UPDATED_SENT_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .locked(UPDATED_LOCKED)
            .lockedBy(UPDATED_LOCKED_BY);
        return rFI;
    }

    @BeforeEach
    public void initTest() {
        rFIRepository.deleteAll();
        rFI = createEntity();
    }

    @Test
    public void createRFI() throws Exception {
        int databaseSizeBeforeCreate = rFIRepository.findAll().size();
        // Create the RFI
        RFIDTO rFIDTO = rFIMapper.toDto(rFI);
        restRFIMockMvc.perform(post("/api/rfis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFIDTO)))
            .andExpect(status().isCreated());

        // Validate the RFI in the database
        List<RFI> rFIList = rFIRepository.findAll();
        assertThat(rFIList).hasSize(databaseSizeBeforeCreate + 1);
        RFI testRFI = rFIList.get(rFIList.size() - 1);
        assertThat(testRFI.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRFI.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testRFI.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testRFI.getSentDate()).isEqualTo(DEFAULT_SENT_DATE);
        assertThat(testRFI.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testRFI.isLocked()).isEqualTo(DEFAULT_LOCKED);
        assertThat(testRFI.getLockedBy()).isEqualTo(DEFAULT_LOCKED_BY);

        // Validate the RFI in Elasticsearch
        verify(mockRFISearchRepository, times(1)).save(testRFI);
    }

    @Test
    public void createRFIWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rFIRepository.findAll().size();

        // Create the RFI with an existing ID
        rFI.setId("existing_id");
        RFIDTO rFIDTO = rFIMapper.toDto(rFI);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFIMockMvc.perform(post("/api/rfis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFIDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RFI in the database
        List<RFI> rFIList = rFIRepository.findAll();
        assertThat(rFIList).hasSize(databaseSizeBeforeCreate);

        // Validate the RFI in Elasticsearch
        verify(mockRFISearchRepository, times(0)).save(rFI);
    }


    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = rFIRepository.findAll().size();
        // set the field null
        rFI.setTitle(null);

        // Create the RFI, which fails.
        RFIDTO rFIDTO = rFIMapper.toDto(rFI);


        restRFIMockMvc.perform(post("/api/rfis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFIDTO)))
            .andExpect(status().isBadRequest());

        List<RFI> rFIList = rFIRepository.findAll();
        assertThat(rFIList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = rFIRepository.findAll().size();
        // set the field null
        rFI.setQuestion(null);

        // Create the RFI, which fails.
        RFIDTO rFIDTO = rFIMapper.toDto(rFI);


        restRFIMockMvc.perform(post("/api/rfis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFIDTO)))
            .andExpect(status().isBadRequest());

        List<RFI> rFIList = rFIRepository.findAll();
        assertThat(rFIList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRFIS() throws Exception {
        // Initialize the database
        rFIRepository.save(rFI);

        // Get all the rFIList
        restRFIMockMvc.perform(get("/api/rfis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFI.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].sentDate").value(hasItem(DEFAULT_SENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].lockedBy").value(hasItem(DEFAULT_LOCKED_BY)));
    }
    
    @Test
    public void getRFI() throws Exception {
        // Initialize the database
        rFIRepository.save(rFI);

        // Get the rFI
        restRFIMockMvc.perform(get("/api/rfis/{id}", rFI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFI.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER))
            .andExpect(jsonPath("$.sentDate").value(DEFAULT_SENT_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.locked").value(DEFAULT_LOCKED.booleanValue()))
            .andExpect(jsonPath("$.lockedBy").value(DEFAULT_LOCKED_BY));
    }
    @Test
    public void getNonExistingRFI() throws Exception {
        // Get the rFI
        restRFIMockMvc.perform(get("/api/rfis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRFI() throws Exception {
        // Initialize the database
        rFIRepository.save(rFI);

        int databaseSizeBeforeUpdate = rFIRepository.findAll().size();

        // Update the rFI
        RFI updatedRFI = rFIRepository.findById(rFI.getId()).get();
        updatedRFI
            .title(UPDATED_TITLE)
            .question(UPDATED_QUESTION)
            .answer(UPDATED_ANSWER)
            .sentDate(UPDATED_SENT_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .locked(UPDATED_LOCKED)
            .lockedBy(UPDATED_LOCKED_BY);
        RFIDTO rFIDTO = rFIMapper.toDto(updatedRFI);

        restRFIMockMvc.perform(put("/api/rfis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFIDTO)))
            .andExpect(status().isOk());

        // Validate the RFI in the database
        List<RFI> rFIList = rFIRepository.findAll();
        assertThat(rFIList).hasSize(databaseSizeBeforeUpdate);
        RFI testRFI = rFIList.get(rFIList.size() - 1);
        assertThat(testRFI.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRFI.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testRFI.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testRFI.getSentDate()).isEqualTo(UPDATED_SENT_DATE);
        assertThat(testRFI.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testRFI.isLocked()).isEqualTo(UPDATED_LOCKED);
        assertThat(testRFI.getLockedBy()).isEqualTo(UPDATED_LOCKED_BY);

        // Validate the RFI in Elasticsearch
        verify(mockRFISearchRepository, times(1)).save(testRFI);
    }

    @Test
    public void updateNonExistingRFI() throws Exception {
        int databaseSizeBeforeUpdate = rFIRepository.findAll().size();

        // Create the RFI
        RFIDTO rFIDTO = rFIMapper.toDto(rFI);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFIMockMvc.perform(put("/api/rfis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rFIDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RFI in the database
        List<RFI> rFIList = rFIRepository.findAll();
        assertThat(rFIList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RFI in Elasticsearch
        verify(mockRFISearchRepository, times(0)).save(rFI);
    }

    @Test
    public void deleteRFI() throws Exception {
        // Initialize the database
        rFIRepository.save(rFI);

        int databaseSizeBeforeDelete = rFIRepository.findAll().size();

        // Delete the rFI
        restRFIMockMvc.perform(delete("/api/rfis/{id}", rFI.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFI> rFIList = rFIRepository.findAll();
        assertThat(rFIList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RFI in Elasticsearch
        verify(mockRFISearchRepository, times(1)).deleteById(rFI.getId());
    }

    @Test
    public void searchRFI() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        rFIRepository.save(rFI);
        when(mockRFISearchRepository.search(queryStringQuery("id:" + rFI.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(rFI), PageRequest.of(0, 1), 1));

        // Search the rFI
        restRFIMockMvc.perform(get("/api/_search/rfis?query=id:" + rFI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFI.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].sentDate").value(hasItem(DEFAULT_SENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].lockedBy").value(hasItem(DEFAULT_LOCKED_BY)));
    }
}
