package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.SheetComments;
import com.cmspro.base.microservice.repository.SheetCommentsRepository;
import com.cmspro.base.microservice.repository.search.SheetCommentsSearchRepository;
import com.cmspro.base.microservice.service.SheetCommentsService;
import com.cmspro.base.microservice.service.dto.SheetCommentsDTO;
import com.cmspro.base.microservice.service.mapper.SheetCommentsMapper;

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
 * Integration tests for the {@link SheetCommentsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SheetCommentsResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SheetCommentsRepository sheetCommentsRepository;

    @Autowired
    private SheetCommentsMapper sheetCommentsMapper;

    @Autowired
    private SheetCommentsService sheetCommentsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.SheetCommentsSearchRepositoryMockConfiguration
     */
    @Autowired
    private SheetCommentsSearchRepository mockSheetCommentsSearchRepository;

    @Autowired
    private MockMvc restSheetCommentsMockMvc;

    private SheetComments sheetComments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SheetComments createEntity() {
        SheetComments sheetComments = new SheetComments()
            .createdBy(DEFAULT_CREATED_BY)
            .comment(DEFAULT_COMMENT)
            .creationDate(DEFAULT_CREATION_DATE);
        return sheetComments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SheetComments createUpdatedEntity() {
        SheetComments sheetComments = new SheetComments()
            .createdBy(UPDATED_CREATED_BY)
            .comment(UPDATED_COMMENT)
            .creationDate(UPDATED_CREATION_DATE);
        return sheetComments;
    }

    @BeforeEach
    public void initTest() {
        sheetCommentsRepository.deleteAll();
        sheetComments = createEntity();
    }

    @Test
    public void createSheetComments() throws Exception {
        int databaseSizeBeforeCreate = sheetCommentsRepository.findAll().size();
        // Create the SheetComments
        SheetCommentsDTO sheetCommentsDTO = sheetCommentsMapper.toDto(sheetComments);
        restSheetCommentsMockMvc.perform(post("/api/sheet-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetCommentsDTO)))
            .andExpect(status().isCreated());

        // Validate the SheetComments in the database
        List<SheetComments> sheetCommentsList = sheetCommentsRepository.findAll();
        assertThat(sheetCommentsList).hasSize(databaseSizeBeforeCreate + 1);
        SheetComments testSheetComments = sheetCommentsList.get(sheetCommentsList.size() - 1);
        assertThat(testSheetComments.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSheetComments.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testSheetComments.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the SheetComments in Elasticsearch
        verify(mockSheetCommentsSearchRepository, times(1)).save(testSheetComments);
    }

    @Test
    public void createSheetCommentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sheetCommentsRepository.findAll().size();

        // Create the SheetComments with an existing ID
        sheetComments.setId("existing_id");
        SheetCommentsDTO sheetCommentsDTO = sheetCommentsMapper.toDto(sheetComments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSheetCommentsMockMvc.perform(post("/api/sheet-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetCommentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SheetComments in the database
        List<SheetComments> sheetCommentsList = sheetCommentsRepository.findAll();
        assertThat(sheetCommentsList).hasSize(databaseSizeBeforeCreate);

        // Validate the SheetComments in Elasticsearch
        verify(mockSheetCommentsSearchRepository, times(0)).save(sheetComments);
    }


    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetCommentsRepository.findAll().size();
        // set the field null
        sheetComments.setCreatedBy(null);

        // Create the SheetComments, which fails.
        SheetCommentsDTO sheetCommentsDTO = sheetCommentsMapper.toDto(sheetComments);


        restSheetCommentsMockMvc.perform(post("/api/sheet-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetCommentsDTO)))
            .andExpect(status().isBadRequest());

        List<SheetComments> sheetCommentsList = sheetCommentsRepository.findAll();
        assertThat(sheetCommentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetCommentsRepository.findAll().size();
        // set the field null
        sheetComments.setComment(null);

        // Create the SheetComments, which fails.
        SheetCommentsDTO sheetCommentsDTO = sheetCommentsMapper.toDto(sheetComments);


        restSheetCommentsMockMvc.perform(post("/api/sheet-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetCommentsDTO)))
            .andExpect(status().isBadRequest());

        List<SheetComments> sheetCommentsList = sheetCommentsRepository.findAll();
        assertThat(sheetCommentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetCommentsRepository.findAll().size();
        // set the field null
        sheetComments.setCreationDate(null);

        // Create the SheetComments, which fails.
        SheetCommentsDTO sheetCommentsDTO = sheetCommentsMapper.toDto(sheetComments);


        restSheetCommentsMockMvc.perform(post("/api/sheet-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetCommentsDTO)))
            .andExpect(status().isBadRequest());

        List<SheetComments> sheetCommentsList = sheetCommentsRepository.findAll();
        assertThat(sheetCommentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSheetComments() throws Exception {
        // Initialize the database
        sheetCommentsRepository.save(sheetComments);

        // Get all the sheetCommentsList
        restSheetCommentsMockMvc.perform(get("/api/sheet-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sheetComments.getId())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getSheetComments() throws Exception {
        // Initialize the database
        sheetCommentsRepository.save(sheetComments);

        // Get the sheetComments
        restSheetCommentsMockMvc.perform(get("/api/sheet-comments/{id}", sheetComments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sheetComments.getId()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingSheetComments() throws Exception {
        // Get the sheetComments
        restSheetCommentsMockMvc.perform(get("/api/sheet-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSheetComments() throws Exception {
        // Initialize the database
        sheetCommentsRepository.save(sheetComments);

        int databaseSizeBeforeUpdate = sheetCommentsRepository.findAll().size();

        // Update the sheetComments
        SheetComments updatedSheetComments = sheetCommentsRepository.findById(sheetComments.getId()).get();
        updatedSheetComments
            .createdBy(UPDATED_CREATED_BY)
            .comment(UPDATED_COMMENT)
            .creationDate(UPDATED_CREATION_DATE);
        SheetCommentsDTO sheetCommentsDTO = sheetCommentsMapper.toDto(updatedSheetComments);

        restSheetCommentsMockMvc.perform(put("/api/sheet-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetCommentsDTO)))
            .andExpect(status().isOk());

        // Validate the SheetComments in the database
        List<SheetComments> sheetCommentsList = sheetCommentsRepository.findAll();
        assertThat(sheetCommentsList).hasSize(databaseSizeBeforeUpdate);
        SheetComments testSheetComments = sheetCommentsList.get(sheetCommentsList.size() - 1);
        assertThat(testSheetComments.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSheetComments.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testSheetComments.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the SheetComments in Elasticsearch
        verify(mockSheetCommentsSearchRepository, times(1)).save(testSheetComments);
    }

    @Test
    public void updateNonExistingSheetComments() throws Exception {
        int databaseSizeBeforeUpdate = sheetCommentsRepository.findAll().size();

        // Create the SheetComments
        SheetCommentsDTO sheetCommentsDTO = sheetCommentsMapper.toDto(sheetComments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSheetCommentsMockMvc.perform(put("/api/sheet-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetCommentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SheetComments in the database
        List<SheetComments> sheetCommentsList = sheetCommentsRepository.findAll();
        assertThat(sheetCommentsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SheetComments in Elasticsearch
        verify(mockSheetCommentsSearchRepository, times(0)).save(sheetComments);
    }

    @Test
    public void deleteSheetComments() throws Exception {
        // Initialize the database
        sheetCommentsRepository.save(sheetComments);

        int databaseSizeBeforeDelete = sheetCommentsRepository.findAll().size();

        // Delete the sheetComments
        restSheetCommentsMockMvc.perform(delete("/api/sheet-comments/{id}", sheetComments.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SheetComments> sheetCommentsList = sheetCommentsRepository.findAll();
        assertThat(sheetCommentsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SheetComments in Elasticsearch
        verify(mockSheetCommentsSearchRepository, times(1)).deleteById(sheetComments.getId());
    }

    @Test
    public void searchSheetComments() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        sheetCommentsRepository.save(sheetComments);
        when(mockSheetCommentsSearchRepository.search(queryStringQuery("id:" + sheetComments.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(sheetComments), PageRequest.of(0, 1), 1));

        // Search the sheetComments
        restSheetCommentsMockMvc.perform(get("/api/_search/sheet-comments?query=id:" + sheetComments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sheetComments.getId())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
