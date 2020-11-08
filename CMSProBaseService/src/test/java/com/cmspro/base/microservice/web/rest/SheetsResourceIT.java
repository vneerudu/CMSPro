package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.Sheets;
import com.cmspro.base.microservice.repository.SheetsRepository;
import com.cmspro.base.microservice.repository.search.SheetsSearchRepository;
import com.cmspro.base.microservice.service.SheetsService;
import com.cmspro.base.microservice.service.dto.SheetsDTO;
import com.cmspro.base.microservice.service.mapper.SheetsMapper;

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
 * Integration tests for the {@link SheetsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SheetsResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SheetsRepository sheetsRepository;

    @Autowired
    private SheetsMapper sheetsMapper;

    @Autowired
    private SheetsService sheetsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.SheetsSearchRepositoryMockConfiguration
     */
    @Autowired
    private SheetsSearchRepository mockSheetsSearchRepository;

    @Autowired
    private MockMvc restSheetsMockMvc;

    private Sheets sheets;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sheets createEntity() {
        Sheets sheets = new Sheets()
            .number(DEFAULT_NUMBER)
            .title(DEFAULT_TITLE)
            .version(DEFAULT_VERSION)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return sheets;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sheets createUpdatedEntity() {
        Sheets sheets = new Sheets()
            .number(UPDATED_NUMBER)
            .title(UPDATED_TITLE)
            .version(UPDATED_VERSION)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return sheets;
    }

    @BeforeEach
    public void initTest() {
        sheetsRepository.deleteAll();
        sheets = createEntity();
    }

    @Test
    public void createSheets() throws Exception {
        int databaseSizeBeforeCreate = sheetsRepository.findAll().size();
        // Create the Sheets
        SheetsDTO sheetsDTO = sheetsMapper.toDto(sheets);
        restSheetsMockMvc.perform(post("/api/sheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetsDTO)))
            .andExpect(status().isCreated());

        // Validate the Sheets in the database
        List<Sheets> sheetsList = sheetsRepository.findAll();
        assertThat(sheetsList).hasSize(databaseSizeBeforeCreate + 1);
        Sheets testSheets = sheetsList.get(sheetsList.size() - 1);
        assertThat(testSheets.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testSheets.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSheets.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testSheets.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSheets.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the Sheets in Elasticsearch
        verify(mockSheetsSearchRepository, times(1)).save(testSheets);
    }

    @Test
    public void createSheetsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sheetsRepository.findAll().size();

        // Create the Sheets with an existing ID
        sheets.setId("existing_id");
        SheetsDTO sheetsDTO = sheetsMapper.toDto(sheets);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSheetsMockMvc.perform(post("/api/sheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sheets in the database
        List<Sheets> sheetsList = sheetsRepository.findAll();
        assertThat(sheetsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Sheets in Elasticsearch
        verify(mockSheetsSearchRepository, times(0)).save(sheets);
    }


    @Test
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetsRepository.findAll().size();
        // set the field null
        sheets.setNumber(null);

        // Create the Sheets, which fails.
        SheetsDTO sheetsDTO = sheetsMapper.toDto(sheets);


        restSheetsMockMvc.perform(post("/api/sheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetsDTO)))
            .andExpect(status().isBadRequest());

        List<Sheets> sheetsList = sheetsRepository.findAll();
        assertThat(sheetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetsRepository.findAll().size();
        // set the field null
        sheets.setVersion(null);

        // Create the Sheets, which fails.
        SheetsDTO sheetsDTO = sheetsMapper.toDto(sheets);


        restSheetsMockMvc.perform(post("/api/sheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetsDTO)))
            .andExpect(status().isBadRequest());

        List<Sheets> sheetsList = sheetsRepository.findAll();
        assertThat(sheetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetsRepository.findAll().size();
        // set the field null
        sheets.setCreatedBy(null);

        // Create the Sheets, which fails.
        SheetsDTO sheetsDTO = sheetsMapper.toDto(sheets);


        restSheetsMockMvc.perform(post("/api/sheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetsDTO)))
            .andExpect(status().isBadRequest());

        List<Sheets> sheetsList = sheetsRepository.findAll();
        assertThat(sheetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetsRepository.findAll().size();
        // set the field null
        sheets.setCreationDate(null);

        // Create the Sheets, which fails.
        SheetsDTO sheetsDTO = sheetsMapper.toDto(sheets);


        restSheetsMockMvc.perform(post("/api/sheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetsDTO)))
            .andExpect(status().isBadRequest());

        List<Sheets> sheetsList = sheetsRepository.findAll();
        assertThat(sheetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSheets() throws Exception {
        // Initialize the database
        sheetsRepository.save(sheets);

        // Get all the sheetsList
        restSheetsMockMvc.perform(get("/api/sheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sheets.getId())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getSheets() throws Exception {
        // Initialize the database
        sheetsRepository.save(sheets);

        // Get the sheets
        restSheetsMockMvc.perform(get("/api/sheets/{id}", sheets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sheets.getId()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingSheets() throws Exception {
        // Get the sheets
        restSheetsMockMvc.perform(get("/api/sheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSheets() throws Exception {
        // Initialize the database
        sheetsRepository.save(sheets);

        int databaseSizeBeforeUpdate = sheetsRepository.findAll().size();

        // Update the sheets
        Sheets updatedSheets = sheetsRepository.findById(sheets.getId()).get();
        updatedSheets
            .number(UPDATED_NUMBER)
            .title(UPDATED_TITLE)
            .version(UPDATED_VERSION)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        SheetsDTO sheetsDTO = sheetsMapper.toDto(updatedSheets);

        restSheetsMockMvc.perform(put("/api/sheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetsDTO)))
            .andExpect(status().isOk());

        // Validate the Sheets in the database
        List<Sheets> sheetsList = sheetsRepository.findAll();
        assertThat(sheetsList).hasSize(databaseSizeBeforeUpdate);
        Sheets testSheets = sheetsList.get(sheetsList.size() - 1);
        assertThat(testSheets.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testSheets.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSheets.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSheets.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSheets.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the Sheets in Elasticsearch
        verify(mockSheetsSearchRepository, times(1)).save(testSheets);
    }

    @Test
    public void updateNonExistingSheets() throws Exception {
        int databaseSizeBeforeUpdate = sheetsRepository.findAll().size();

        // Create the Sheets
        SheetsDTO sheetsDTO = sheetsMapper.toDto(sheets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSheetsMockMvc.perform(put("/api/sheets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sheets in the database
        List<Sheets> sheetsList = sheetsRepository.findAll();
        assertThat(sheetsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Sheets in Elasticsearch
        verify(mockSheetsSearchRepository, times(0)).save(sheets);
    }

    @Test
    public void deleteSheets() throws Exception {
        // Initialize the database
        sheetsRepository.save(sheets);

        int databaseSizeBeforeDelete = sheetsRepository.findAll().size();

        // Delete the sheets
        restSheetsMockMvc.perform(delete("/api/sheets/{id}", sheets.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sheets> sheetsList = sheetsRepository.findAll();
        assertThat(sheetsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Sheets in Elasticsearch
        verify(mockSheetsSearchRepository, times(1)).deleteById(sheets.getId());
    }

    @Test
    public void searchSheets() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        sheetsRepository.save(sheets);
        when(mockSheetsSearchRepository.search(queryStringQuery("id:" + sheets.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(sheets), PageRequest.of(0, 1), 1));

        // Search the sheets
        restSheetsMockMvc.perform(get("/api/_search/sheets?query=id:" + sheets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sheets.getId())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
