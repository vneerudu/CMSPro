package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.UserGroups;
import com.cmspro.base.microservice.repository.UserGroupsRepository;
import com.cmspro.base.microservice.repository.search.UserGroupsSearchRepository;
import com.cmspro.base.microservice.service.UserGroupsService;
import com.cmspro.base.microservice.service.dto.UserGroupsDTO;
import com.cmspro.base.microservice.service.mapper.UserGroupsMapper;

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
 * Integration tests for the {@link UserGroupsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserGroupsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private UserGroupsRepository userGroupsRepository;

    @Autowired
    private UserGroupsMapper userGroupsMapper;

    @Autowired
    private UserGroupsService userGroupsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.UserGroupsSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserGroupsSearchRepository mockUserGroupsSearchRepository;

    @Autowired
    private MockMvc restUserGroupsMockMvc;

    private UserGroups userGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroups createEntity() {
        UserGroups userGroups = new UserGroups()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return userGroups;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroups createUpdatedEntity() {
        UserGroups userGroups = new UserGroups()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return userGroups;
    }

    @BeforeEach
    public void initTest() {
        userGroupsRepository.deleteAll();
        userGroups = createEntity();
    }

    @Test
    public void createUserGroups() throws Exception {
        int databaseSizeBeforeCreate = userGroupsRepository.findAll().size();
        // Create the UserGroups
        UserGroupsDTO userGroupsDTO = userGroupsMapper.toDto(userGroups);
        restUserGroupsMockMvc.perform(post("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserGroups in the database
        List<UserGroups> userGroupsList = userGroupsRepository.findAll();
        assertThat(userGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        UserGroups testUserGroups = userGroupsList.get(userGroupsList.size() - 1);
        assertThat(testUserGroups.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUserGroups.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the UserGroups in Elasticsearch
        verify(mockUserGroupsSearchRepository, times(1)).save(testUserGroups);
    }

    @Test
    public void createUserGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userGroupsRepository.findAll().size();

        // Create the UserGroups with an existing ID
        userGroups.setId("existing_id");
        UserGroupsDTO userGroupsDTO = userGroupsMapper.toDto(userGroups);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGroupsMockMvc.perform(post("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroups in the database
        List<UserGroups> userGroupsList = userGroupsRepository.findAll();
        assertThat(userGroupsList).hasSize(databaseSizeBeforeCreate);

        // Validate the UserGroups in Elasticsearch
        verify(mockUserGroupsSearchRepository, times(0)).save(userGroups);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGroupsRepository.findAll().size();
        // set the field null
        userGroups.setCode(null);

        // Create the UserGroups, which fails.
        UserGroupsDTO userGroupsDTO = userGroupsMapper.toDto(userGroups);


        restUserGroupsMockMvc.perform(post("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<UserGroups> userGroupsList = userGroupsRepository.findAll();
        assertThat(userGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGroupsRepository.findAll().size();
        // set the field null
        userGroups.setDescription(null);

        // Create the UserGroups, which fails.
        UserGroupsDTO userGroupsDTO = userGroupsMapper.toDto(userGroups);


        restUserGroupsMockMvc.perform(post("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<UserGroups> userGroupsList = userGroupsRepository.findAll();
        assertThat(userGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllUserGroups() throws Exception {
        // Initialize the database
        userGroupsRepository.save(userGroups);

        // Get all the userGroupsList
        restUserGroupsMockMvc.perform(get("/api/user-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroups.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    public void getUserGroups() throws Exception {
        // Initialize the database
        userGroupsRepository.save(userGroups);

        // Get the userGroups
        restUserGroupsMockMvc.perform(get("/api/user-groups/{id}", userGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userGroups.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    public void getNonExistingUserGroups() throws Exception {
        // Get the userGroups
        restUserGroupsMockMvc.perform(get("/api/user-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserGroups() throws Exception {
        // Initialize the database
        userGroupsRepository.save(userGroups);

        int databaseSizeBeforeUpdate = userGroupsRepository.findAll().size();

        // Update the userGroups
        UserGroups updatedUserGroups = userGroupsRepository.findById(userGroups.getId()).get();
        updatedUserGroups
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        UserGroupsDTO userGroupsDTO = userGroupsMapper.toDto(updatedUserGroups);

        restUserGroupsMockMvc.perform(put("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupsDTO)))
            .andExpect(status().isOk());

        // Validate the UserGroups in the database
        List<UserGroups> userGroupsList = userGroupsRepository.findAll();
        assertThat(userGroupsList).hasSize(databaseSizeBeforeUpdate);
        UserGroups testUserGroups = userGroupsList.get(userGroupsList.size() - 1);
        assertThat(testUserGroups.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserGroups.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the UserGroups in Elasticsearch
        verify(mockUserGroupsSearchRepository, times(1)).save(testUserGroups);
    }

    @Test
    public void updateNonExistingUserGroups() throws Exception {
        int databaseSizeBeforeUpdate = userGroupsRepository.findAll().size();

        // Create the UserGroups
        UserGroupsDTO userGroupsDTO = userGroupsMapper.toDto(userGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGroupsMockMvc.perform(put("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroups in the database
        List<UserGroups> userGroupsList = userGroupsRepository.findAll();
        assertThat(userGroupsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UserGroups in Elasticsearch
        verify(mockUserGroupsSearchRepository, times(0)).save(userGroups);
    }

    @Test
    public void deleteUserGroups() throws Exception {
        // Initialize the database
        userGroupsRepository.save(userGroups);

        int databaseSizeBeforeDelete = userGroupsRepository.findAll().size();

        // Delete the userGroups
        restUserGroupsMockMvc.perform(delete("/api/user-groups/{id}", userGroups.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGroups> userGroupsList = userGroupsRepository.findAll();
        assertThat(userGroupsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UserGroups in Elasticsearch
        verify(mockUserGroupsSearchRepository, times(1)).deleteById(userGroups.getId());
    }

    @Test
    public void searchUserGroups() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        userGroupsRepository.save(userGroups);
        when(mockUserGroupsSearchRepository.search(queryStringQuery("id:" + userGroups.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(userGroups), PageRequest.of(0, 1), 1));

        // Search the userGroups
        restUserGroupsMockMvc.perform(get("/api/_search/user-groups?query=id:" + userGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroups.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
