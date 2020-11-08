package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.SheetHistory;
import com.cmspro.base.microservice.repository.SheetHistoryRepository;
import com.cmspro.base.microservice.repository.search.SheetHistorySearchRepository;
import com.cmspro.base.microservice.service.SheetHistoryService;
import com.cmspro.base.microservice.service.dto.SheetHistoryDTO;
import com.cmspro.base.microservice.service.mapper.SheetHistoryMapper;

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
 * Integration tests for the {@link SheetHistoryResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SheetHistoryResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SheetHistoryRepository sheetHistoryRepository;

    @Autowired
    private SheetHistoryMapper sheetHistoryMapper;

    @Autowired
    private SheetHistoryService sheetHistoryService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.SheetHistorySearchRepositoryMockConfiguration
     */
    @Autowired
    private SheetHistorySearchRepository mockSheetHistorySearchRepository;

    @Autowired
    private MockMvc restSheetHistoryMockMvc;

    private SheetHistory sheetHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SheetHistory createEntity() {
        SheetHistory sheetHistory = new SheetHistory()
            .number(DEFAULT_NUMBER)
            .version(DEFAULT_VERSION)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return sheetHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SheetHistory createUpdatedEntity() {
        SheetHistory sheetHistory = new SheetHistory()
            .number(UPDATED_NUMBER)
            .version(UPDATED_VERSION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return sheetHistory;
    }

    @BeforeEach
    public void initTest() {
        sheetHistoryRepository.deleteAll();
        sheetHistory = createEntity();
    }

    @Test
    public void createSheetHistory() throws Exception {
        int databaseSizeBeforeCreate = sheetHistoryRepository.findAll().size();
        // Create the SheetHistory
        SheetHistoryDTO sheetHistoryDTO = sheetHistoryMapper.toDto(sheetHistory);
        restSheetHistoryMockMvc.perform(post("/api/sheet-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the SheetHistory in the database
        List<SheetHistory> sheetHistoryList = sheetHistoryRepository.findAll();
        assertThat(sheetHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        SheetHistory testSheetHistory = sheetHistoryList.get(sheetHistoryList.size() - 1);
        assertThat(testSheetHistory.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testSheetHistory.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testSheetHistory.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSheetHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSheetHistory.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the SheetHistory in Elasticsearch
        verify(mockSheetHistorySearchRepository, times(1)).save(testSheetHistory);
    }

    @Test
    public void createSheetHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sheetHistoryRepository.findAll().size();

        // Create the SheetHistory with an existing ID
        sheetHistory.setId("existing_id");
        SheetHistoryDTO sheetHistoryDTO = sheetHistoryMapper.toDto(sheetHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSheetHistoryMockMvc.perform(post("/api/sheet-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SheetHistory in the database
        List<SheetHistory> sheetHistoryList = sheetHistoryRepository.findAll();
        assertThat(sheetHistoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the SheetHistory in Elasticsearch
        verify(mockSheetHistorySearchRepository, times(0)).save(sheetHistory);
    }


    @Test
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetHistoryRepository.findAll().size();
        // set the field null
        sheetHistory.setNumber(null);

        // Create the SheetHistory, which fails.
        SheetHistoryDTO sheetHistoryDTO = sheetHistoryMapper.toDto(sheetHistory);


        restSheetHistoryMockMvc.perform(post("/api/sheet-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<SheetHistory> sheetHistoryList = sheetHistoryRepository.findAll();
        assertThat(sheetHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetHistoryRepository.findAll().size();
        // set the field null
        sheetHistory.setIsActive(null);

        // Create the SheetHistory, which fails.
        SheetHistoryDTO sheetHistoryDTO = sheetHistoryMapper.toDto(sheetHistory);


        restSheetHistoryMockMvc.perform(post("/api/sheet-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<SheetHistory> sheetHistoryList = sheetHistoryRepository.findAll();
        assertThat(sheetHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetHistoryRepository.findAll().size();
        // set the field null
        sheetHistory.setCreatedBy(null);

        // Create the SheetHistory, which fails.
        SheetHistoryDTO sheetHistoryDTO = sheetHistoryMapper.toDto(sheetHistory);


        restSheetHistoryMockMvc.perform(post("/api/sheet-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<SheetHistory> sheetHistoryList = sheetHistoryRepository.findAll();
        assertThat(sheetHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetHistoryRepository.findAll().size();
        // set the field null
        sheetHistory.setCreationDate(null);

        // Create the SheetHistory, which fails.
        SheetHistoryDTO sheetHistoryDTO = sheetHistoryMapper.toDto(sheetHistory);


        restSheetHistoryMockMvc.perform(post("/api/sheet-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<SheetHistory> sheetHistoryList = sheetHistoryRepository.findAll();
        assertThat(sheetHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSheetHistories() throws Exception {
        // Initialize the database
        sheetHistoryRepository.save(sheetHistory);

        // Get all the sheetHistoryList
        restSheetHistoryMockMvc.perform(get("/api/sheet-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sheetHistory.getId())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getSheetHistory() throws Exception {
        // Initialize the database
        sheetHistoryRepository.save(sheetHistory);

        // Get the sheetHistory
        restSheetHistoryMockMvc.perform(get("/api/sheet-histories/{id}", sheetHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sheetHistory.getId()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingSheetHistory() throws Exception {
        // Get the sheetHistory
        restSheetHistoryMockMvc.perform(get("/api/sheet-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSheetHistory() throws Exception {
        // Initialize the database
        sheetHistoryRepository.save(sheetHistory);

        int databaseSizeBeforeUpdate = sheetHistoryRepository.findAll().size();

        // Update the sheetHistory
        SheetHistory updatedSheetHistory = sheetHistoryRepository.findById(sheetHistory.getId()).get();
        updatedSheetHistory
            .number(UPDATED_NUMBER)
            .version(UPDATED_VERSION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        SheetHistoryDTO sheetHistoryDTO = sheetHistoryMapper.toDto(updatedSheetHistory);

        restSheetHistoryMockMvc.perform(put("/api/sheet-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the SheetHistory in the database
        List<SheetHistory> sheetHistoryList = sheetHistoryRepository.findAll();
        assertThat(sheetHistoryList).hasSize(databaseSizeBeforeUpdate);
        SheetHistory testSheetHistory = sheetHistoryList.get(sheetHistoryList.size() - 1);
        assertThat(testSheetHistory.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testSheetHistory.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSheetHistory.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSheetHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSheetHistory.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the SheetHistory in Elasticsearch
        verify(mockSheetHistorySearchRepository, times(1)).save(testSheetHistory);
    }

    @Test
    public void updateNonExistingSheetHistory() throws Exception {
        int databaseSizeBeforeUpdate = sheetHistoryRepository.findAll().size();

        // Create the SheetHistory
        SheetHistoryDTO sheetHistoryDTO = sheetHistoryMapper.toDto(sheetHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSheetHistoryMockMvc.perform(put("/api/sheet-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SheetHistory in the database
        List<SheetHistory> sheetHistoryList = sheetHistoryRepository.findAll();
        assertThat(sheetHistoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SheetHistory in Elasticsearch
        verify(mockSheetHistorySearchRepository, times(0)).save(sheetHistory);
    }

    @Test
    public void deleteSheetHistory() throws Exception {
        // Initialize the database
        sheetHistoryRepository.save(sheetHistory);

        int databaseSizeBeforeDelete = sheetHistoryRepository.findAll().size();

        // Delete the sheetHistory
        restSheetHistoryMockMvc.perform(delete("/api/sheet-histories/{id}", sheetHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SheetHistory> sheetHistoryList = sheetHistoryRepository.findAll();
        assertThat(sheetHistoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SheetHistory in Elasticsearch
        verify(mockSheetHistorySearchRepository, times(1)).deleteById(sheetHistory.getId());
    }

    @Test
    public void searchSheetHistory() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        sheetHistoryRepository.save(sheetHistory);
        when(mockSheetHistorySearchRepository.search(queryStringQuery("id:" + sheetHistory.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(sheetHistory), PageRequest.of(0, 1), 1));

        // Search the sheetHistory
        restSheetHistoryMockMvc.perform(get("/api/_search/sheet-histories?query=id:" + sheetHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sheetHistory.getId())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
