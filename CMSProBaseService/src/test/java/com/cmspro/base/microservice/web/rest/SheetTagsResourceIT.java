package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.SheetTags;
import com.cmspro.base.microservice.repository.SheetTagsRepository;
import com.cmspro.base.microservice.repository.search.SheetTagsSearchRepository;
import com.cmspro.base.microservice.service.SheetTagsService;
import com.cmspro.base.microservice.service.dto.SheetTagsDTO;
import com.cmspro.base.microservice.service.mapper.SheetTagsMapper;

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

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SheetTagsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SheetTagsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private SheetTagsRepository sheetTagsRepository;

    @Autowired
    private SheetTagsMapper sheetTagsMapper;

    @Autowired
    private SheetTagsService sheetTagsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.SheetTagsSearchRepositoryMockConfiguration
     */
    @Autowired
    private SheetTagsSearchRepository mockSheetTagsSearchRepository;

    @Autowired
    private MockMvc restSheetTagsMockMvc;

    private SheetTags sheetTags;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SheetTags createEntity() {
        SheetTags sheetTags = new SheetTags()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return sheetTags;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SheetTags createUpdatedEntity() {
        SheetTags sheetTags = new SheetTags()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return sheetTags;
    }

    @BeforeEach
    public void initTest() {
        sheetTagsRepository.deleteAll();
        sheetTags = createEntity();
    }

    @Test
    public void createSheetTags() throws Exception {
        int databaseSizeBeforeCreate = sheetTagsRepository.findAll().size();
        // Create the SheetTags
        SheetTagsDTO sheetTagsDTO = sheetTagsMapper.toDto(sheetTags);
        restSheetTagsMockMvc.perform(post("/api/sheet-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetTagsDTO)))
            .andExpect(status().isCreated());

        // Validate the SheetTags in the database
        List<SheetTags> sheetTagsList = sheetTagsRepository.findAll();
        assertThat(sheetTagsList).hasSize(databaseSizeBeforeCreate + 1);
        SheetTags testSheetTags = sheetTagsList.get(sheetTagsList.size() - 1);
        assertThat(testSheetTags.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSheetTags.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSheetTags.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the SheetTags in Elasticsearch
        verify(mockSheetTagsSearchRepository, times(1)).save(testSheetTags);
    }

    @Test
    public void createSheetTagsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sheetTagsRepository.findAll().size();

        // Create the SheetTags with an existing ID
        sheetTags.setId("existing_id");
        SheetTagsDTO sheetTagsDTO = sheetTagsMapper.toDto(sheetTags);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSheetTagsMockMvc.perform(post("/api/sheet-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetTagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SheetTags in the database
        List<SheetTags> sheetTagsList = sheetTagsRepository.findAll();
        assertThat(sheetTagsList).hasSize(databaseSizeBeforeCreate);

        // Validate the SheetTags in Elasticsearch
        verify(mockSheetTagsSearchRepository, times(0)).save(sheetTags);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetTagsRepository.findAll().size();
        // set the field null
        sheetTags.setCode(null);

        // Create the SheetTags, which fails.
        SheetTagsDTO sheetTagsDTO = sheetTagsMapper.toDto(sheetTags);


        restSheetTagsMockMvc.perform(post("/api/sheet-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetTagsDTO)))
            .andExpect(status().isBadRequest());

        List<SheetTags> sheetTagsList = sheetTagsRepository.findAll();
        assertThat(sheetTagsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheetTagsRepository.findAll().size();
        // set the field null
        sheetTags.setDescription(null);

        // Create the SheetTags, which fails.
        SheetTagsDTO sheetTagsDTO = sheetTagsMapper.toDto(sheetTags);


        restSheetTagsMockMvc.perform(post("/api/sheet-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetTagsDTO)))
            .andExpect(status().isBadRequest());

        List<SheetTags> sheetTagsList = sheetTagsRepository.findAll();
        assertThat(sheetTagsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSheetTags() throws Exception {
        // Initialize the database
        sheetTagsRepository.save(sheetTags);

        // Get all the sheetTagsList
        restSheetTagsMockMvc.perform(get("/api/sheet-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sheetTags.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getSheetTags() throws Exception {
        // Initialize the database
        sheetTagsRepository.save(sheetTags);

        // Get the sheetTags
        restSheetTagsMockMvc.perform(get("/api/sheet-tags/{id}", sheetTags.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sheetTags.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    public void getNonExistingSheetTags() throws Exception {
        // Get the sheetTags
        restSheetTagsMockMvc.perform(get("/api/sheet-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSheetTags() throws Exception {
        // Initialize the database
        sheetTagsRepository.save(sheetTags);

        int databaseSizeBeforeUpdate = sheetTagsRepository.findAll().size();

        // Update the sheetTags
        SheetTags updatedSheetTags = sheetTagsRepository.findById(sheetTags.getId()).get();
        updatedSheetTags
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        SheetTagsDTO sheetTagsDTO = sheetTagsMapper.toDto(updatedSheetTags);

        restSheetTagsMockMvc.perform(put("/api/sheet-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetTagsDTO)))
            .andExpect(status().isOk());

        // Validate the SheetTags in the database
        List<SheetTags> sheetTagsList = sheetTagsRepository.findAll();
        assertThat(sheetTagsList).hasSize(databaseSizeBeforeUpdate);
        SheetTags testSheetTags = sheetTagsList.get(sheetTagsList.size() - 1);
        assertThat(testSheetTags.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSheetTags.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSheetTags.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the SheetTags in Elasticsearch
        verify(mockSheetTagsSearchRepository, times(1)).save(testSheetTags);
    }

    @Test
    public void updateNonExistingSheetTags() throws Exception {
        int databaseSizeBeforeUpdate = sheetTagsRepository.findAll().size();

        // Create the SheetTags
        SheetTagsDTO sheetTagsDTO = sheetTagsMapper.toDto(sheetTags);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSheetTagsMockMvc.perform(put("/api/sheet-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sheetTagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SheetTags in the database
        List<SheetTags> sheetTagsList = sheetTagsRepository.findAll();
        assertThat(sheetTagsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SheetTags in Elasticsearch
        verify(mockSheetTagsSearchRepository, times(0)).save(sheetTags);
    }

    @Test
    public void deleteSheetTags() throws Exception {
        // Initialize the database
        sheetTagsRepository.save(sheetTags);

        int databaseSizeBeforeDelete = sheetTagsRepository.findAll().size();

        // Delete the sheetTags
        restSheetTagsMockMvc.perform(delete("/api/sheet-tags/{id}", sheetTags.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SheetTags> sheetTagsList = sheetTagsRepository.findAll();
        assertThat(sheetTagsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SheetTags in Elasticsearch
        verify(mockSheetTagsSearchRepository, times(1)).deleteById(sheetTags.getId());
    }

    @Test
    public void searchSheetTags() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        sheetTagsRepository.save(sheetTags);
        when(mockSheetTagsSearchRepository.search(queryStringQuery("id:" + sheetTags.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(sheetTags), PageRequest.of(0, 1), 1));

        // Search the sheetTags
        restSheetTagsMockMvc.perform(get("/api/_search/sheet-tags?query=id:" + sheetTags.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sheetTags.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
