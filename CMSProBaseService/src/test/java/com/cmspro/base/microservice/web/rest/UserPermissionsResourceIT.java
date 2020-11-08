package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.UserPermissions;
import com.cmspro.base.microservice.repository.UserPermissionsRepository;
import com.cmspro.base.microservice.repository.search.UserPermissionsSearchRepository;
import com.cmspro.base.microservice.service.UserPermissionsService;
import com.cmspro.base.microservice.service.dto.UserPermissionsDTO;
import com.cmspro.base.microservice.service.mapper.UserPermissionsMapper;

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
 * Integration tests for the {@link UserPermissionsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserPermissionsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private UserPermissionsRepository userPermissionsRepository;

    @Autowired
    private UserPermissionsMapper userPermissionsMapper;

    @Autowired
    private UserPermissionsService userPermissionsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.UserPermissionsSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserPermissionsSearchRepository mockUserPermissionsSearchRepository;

    @Autowired
    private MockMvc restUserPermissionsMockMvc;

    private UserPermissions userPermissions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPermissions createEntity() {
        UserPermissions userPermissions = new UserPermissions()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return userPermissions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPermissions createUpdatedEntity() {
        UserPermissions userPermissions = new UserPermissions()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return userPermissions;
    }

    @BeforeEach
    public void initTest() {
        userPermissionsRepository.deleteAll();
        userPermissions = createEntity();
    }

    @Test
    public void createUserPermissions() throws Exception {
        int databaseSizeBeforeCreate = userPermissionsRepository.findAll().size();
        // Create the UserPermissions
        UserPermissionsDTO userPermissionsDTO = userPermissionsMapper.toDto(userPermissions);
        restUserPermissionsMockMvc.perform(post("/api/user-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPermissionsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserPermissions in the database
        List<UserPermissions> userPermissionsList = userPermissionsRepository.findAll();
        assertThat(userPermissionsList).hasSize(databaseSizeBeforeCreate + 1);
        UserPermissions testUserPermissions = userPermissionsList.get(userPermissionsList.size() - 1);
        assertThat(testUserPermissions.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUserPermissions.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUserPermissions.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the UserPermissions in Elasticsearch
        verify(mockUserPermissionsSearchRepository, times(1)).save(testUserPermissions);
    }

    @Test
    public void createUserPermissionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPermissionsRepository.findAll().size();

        // Create the UserPermissions with an existing ID
        userPermissions.setId("existing_id");
        UserPermissionsDTO userPermissionsDTO = userPermissionsMapper.toDto(userPermissions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPermissionsMockMvc.perform(post("/api/user-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPermissionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserPermissions in the database
        List<UserPermissions> userPermissionsList = userPermissionsRepository.findAll();
        assertThat(userPermissionsList).hasSize(databaseSizeBeforeCreate);

        // Validate the UserPermissions in Elasticsearch
        verify(mockUserPermissionsSearchRepository, times(0)).save(userPermissions);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPermissionsRepository.findAll().size();
        // set the field null
        userPermissions.setCode(null);

        // Create the UserPermissions, which fails.
        UserPermissionsDTO userPermissionsDTO = userPermissionsMapper.toDto(userPermissions);


        restUserPermissionsMockMvc.perform(post("/api/user-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPermissionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserPermissions> userPermissionsList = userPermissionsRepository.findAll();
        assertThat(userPermissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPermissionsRepository.findAll().size();
        // set the field null
        userPermissions.setDescription(null);

        // Create the UserPermissions, which fails.
        UserPermissionsDTO userPermissionsDTO = userPermissionsMapper.toDto(userPermissions);


        restUserPermissionsMockMvc.perform(post("/api/user-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPermissionsDTO)))
            .andExpect(status().isBadRequest());

        List<UserPermissions> userPermissionsList = userPermissionsRepository.findAll();
        assertThat(userPermissionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllUserPermissions() throws Exception {
        // Initialize the database
        userPermissionsRepository.save(userPermissions);

        // Get all the userPermissionsList
        restUserPermissionsMockMvc.perform(get("/api/user-permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPermissions.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getUserPermissions() throws Exception {
        // Initialize the database
        userPermissionsRepository.save(userPermissions);

        // Get the userPermissions
        restUserPermissionsMockMvc.perform(get("/api/user-permissions/{id}", userPermissions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userPermissions.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    public void getNonExistingUserPermissions() throws Exception {
        // Get the userPermissions
        restUserPermissionsMockMvc.perform(get("/api/user-permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserPermissions() throws Exception {
        // Initialize the database
        userPermissionsRepository.save(userPermissions);

        int databaseSizeBeforeUpdate = userPermissionsRepository.findAll().size();

        // Update the userPermissions
        UserPermissions updatedUserPermissions = userPermissionsRepository.findById(userPermissions.getId()).get();
        updatedUserPermissions
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        UserPermissionsDTO userPermissionsDTO = userPermissionsMapper.toDto(updatedUserPermissions);

        restUserPermissionsMockMvc.perform(put("/api/user-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPermissionsDTO)))
            .andExpect(status().isOk());

        // Validate the UserPermissions in the database
        List<UserPermissions> userPermissionsList = userPermissionsRepository.findAll();
        assertThat(userPermissionsList).hasSize(databaseSizeBeforeUpdate);
        UserPermissions testUserPermissions = userPermissionsList.get(userPermissionsList.size() - 1);
        assertThat(testUserPermissions.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserPermissions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserPermissions.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the UserPermissions in Elasticsearch
        verify(mockUserPermissionsSearchRepository, times(1)).save(testUserPermissions);
    }

    @Test
    public void updateNonExistingUserPermissions() throws Exception {
        int databaseSizeBeforeUpdate = userPermissionsRepository.findAll().size();

        // Create the UserPermissions
        UserPermissionsDTO userPermissionsDTO = userPermissionsMapper.toDto(userPermissions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPermissionsMockMvc.perform(put("/api/user-permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPermissionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserPermissions in the database
        List<UserPermissions> userPermissionsList = userPermissionsRepository.findAll();
        assertThat(userPermissionsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UserPermissions in Elasticsearch
        verify(mockUserPermissionsSearchRepository, times(0)).save(userPermissions);
    }

    @Test
    public void deleteUserPermissions() throws Exception {
        // Initialize the database
        userPermissionsRepository.save(userPermissions);

        int databaseSizeBeforeDelete = userPermissionsRepository.findAll().size();

        // Delete the userPermissions
        restUserPermissionsMockMvc.perform(delete("/api/user-permissions/{id}", userPermissions.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserPermissions> userPermissionsList = userPermissionsRepository.findAll();
        assertThat(userPermissionsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UserPermissions in Elasticsearch
        verify(mockUserPermissionsSearchRepository, times(1)).deleteById(userPermissions.getId());
    }

    @Test
    public void searchUserPermissions() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        userPermissionsRepository.save(userPermissions);
        when(mockUserPermissionsSearchRepository.search(queryStringQuery("id:" + userPermissions.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(userPermissions), PageRequest.of(0, 1), 1));

        // Search the userPermissions
        restUserPermissionsMockMvc.perform(get("/api/_search/user-permissions?query=id:" + userPermissions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPermissions.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
