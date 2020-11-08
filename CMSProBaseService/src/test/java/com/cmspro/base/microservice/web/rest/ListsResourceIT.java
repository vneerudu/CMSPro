package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.Lists;
import com.cmspro.base.microservice.repository.ListsRepository;
import com.cmspro.base.microservice.repository.search.ListsSearchRepository;
import com.cmspro.base.microservice.service.ListsService;
import com.cmspro.base.microservice.service.dto.ListsDTO;
import com.cmspro.base.microservice.service.mapper.ListsMapper;

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
 * Integration tests for the {@link ListsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ListsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private ListsRepository listsRepository;

    @Autowired
    private ListsMapper listsMapper;

    @Autowired
    private ListsService listsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.ListsSearchRepositoryMockConfiguration
     */
    @Autowired
    private ListsSearchRepository mockListsSearchRepository;

    @Autowired
    private MockMvc restListsMockMvc;

    private Lists lists;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lists createEntity() {
        Lists lists = new Lists()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return lists;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lists createUpdatedEntity() {
        Lists lists = new Lists()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return lists;
    }

    @BeforeEach
    public void initTest() {
        listsRepository.deleteAll();
        lists = createEntity();
    }

    @Test
    public void createLists() throws Exception {
        int databaseSizeBeforeCreate = listsRepository.findAll().size();
        // Create the Lists
        ListsDTO listsDTO = listsMapper.toDto(lists);
        restListsMockMvc.perform(post("/api/lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listsDTO)))
            .andExpect(status().isCreated());

        // Validate the Lists in the database
        List<Lists> listsList = listsRepository.findAll();
        assertThat(listsList).hasSize(databaseSizeBeforeCreate + 1);
        Lists testLists = listsList.get(listsList.size() - 1);
        assertThat(testLists.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLists.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLists.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the Lists in Elasticsearch
        verify(mockListsSearchRepository, times(1)).save(testLists);
    }

    @Test
    public void createListsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listsRepository.findAll().size();

        // Create the Lists with an existing ID
        lists.setId("existing_id");
        ListsDTO listsDTO = listsMapper.toDto(lists);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListsMockMvc.perform(post("/api/lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lists in the database
        List<Lists> listsList = listsRepository.findAll();
        assertThat(listsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Lists in Elasticsearch
        verify(mockListsSearchRepository, times(0)).save(lists);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = listsRepository.findAll().size();
        // set the field null
        lists.setCode(null);

        // Create the Lists, which fails.
        ListsDTO listsDTO = listsMapper.toDto(lists);


        restListsMockMvc.perform(post("/api/lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listsDTO)))
            .andExpect(status().isBadRequest());

        List<Lists> listsList = listsRepository.findAll();
        assertThat(listsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = listsRepository.findAll().size();
        // set the field null
        lists.setDescription(null);

        // Create the Lists, which fails.
        ListsDTO listsDTO = listsMapper.toDto(lists);


        restListsMockMvc.perform(post("/api/lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listsDTO)))
            .andExpect(status().isBadRequest());

        List<Lists> listsList = listsRepository.findAll();
        assertThat(listsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllLists() throws Exception {
        // Initialize the database
        listsRepository.save(lists);

        // Get all the listsList
        restListsMockMvc.perform(get("/api/lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lists.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getLists() throws Exception {
        // Initialize the database
        listsRepository.save(lists);

        // Get the lists
        restListsMockMvc.perform(get("/api/lists/{id}", lists.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lists.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    public void getNonExistingLists() throws Exception {
        // Get the lists
        restListsMockMvc.perform(get("/api/lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateLists() throws Exception {
        // Initialize the database
        listsRepository.save(lists);

        int databaseSizeBeforeUpdate = listsRepository.findAll().size();

        // Update the lists
        Lists updatedLists = listsRepository.findById(lists.getId()).get();
        updatedLists
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        ListsDTO listsDTO = listsMapper.toDto(updatedLists);

        restListsMockMvc.perform(put("/api/lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listsDTO)))
            .andExpect(status().isOk());

        // Validate the Lists in the database
        List<Lists> listsList = listsRepository.findAll();
        assertThat(listsList).hasSize(databaseSizeBeforeUpdate);
        Lists testLists = listsList.get(listsList.size() - 1);
        assertThat(testLists.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLists.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLists.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the Lists in Elasticsearch
        verify(mockListsSearchRepository, times(1)).save(testLists);
    }

    @Test
    public void updateNonExistingLists() throws Exception {
        int databaseSizeBeforeUpdate = listsRepository.findAll().size();

        // Create the Lists
        ListsDTO listsDTO = listsMapper.toDto(lists);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListsMockMvc.perform(put("/api/lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lists in the database
        List<Lists> listsList = listsRepository.findAll();
        assertThat(listsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Lists in Elasticsearch
        verify(mockListsSearchRepository, times(0)).save(lists);
    }

    @Test
    public void deleteLists() throws Exception {
        // Initialize the database
        listsRepository.save(lists);

        int databaseSizeBeforeDelete = listsRepository.findAll().size();

        // Delete the lists
        restListsMockMvc.perform(delete("/api/lists/{id}", lists.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lists> listsList = listsRepository.findAll();
        assertThat(listsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Lists in Elasticsearch
        verify(mockListsSearchRepository, times(1)).deleteById(lists.getId());
    }

    @Test
    public void searchLists() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        listsRepository.save(lists);
        when(mockListsSearchRepository.search(queryStringQuery("id:" + lists.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(lists), PageRequest.of(0, 1), 1));

        // Search the lists
        restListsMockMvc.perform(get("/api/_search/lists?query=id:" + lists.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lists.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
