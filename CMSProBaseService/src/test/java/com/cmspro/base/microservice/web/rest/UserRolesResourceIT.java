package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.UserRoles;
import com.cmspro.base.microservice.repository.UserRolesRepository;
import com.cmspro.base.microservice.repository.search.UserRolesSearchRepository;
import com.cmspro.base.microservice.service.UserRolesService;
import com.cmspro.base.microservice.service.dto.UserRolesDTO;
import com.cmspro.base.microservice.service.mapper.UserRolesMapper;

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
 * Integration tests for the {@link UserRolesResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserRolesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private UserRolesMapper userRolesMapper;

    @Autowired
    private UserRolesService userRolesService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.UserRolesSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserRolesSearchRepository mockUserRolesSearchRepository;

    @Autowired
    private MockMvc restUserRolesMockMvc;

    private UserRoles userRoles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRoles createEntity() {
        UserRoles userRoles = new UserRoles()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return userRoles;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRoles createUpdatedEntity() {
        UserRoles userRoles = new UserRoles()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return userRoles;
    }

    @BeforeEach
    public void initTest() {
        userRolesRepository.deleteAll();
        userRoles = createEntity();
    }

    @Test
    public void createUserRoles() throws Exception {
        int databaseSizeBeforeCreate = userRolesRepository.findAll().size();
        // Create the UserRoles
        UserRolesDTO userRolesDTO = userRolesMapper.toDto(userRoles);
        restUserRolesMockMvc.perform(post("/api/user-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRolesDTO)))
            .andExpect(status().isCreated());

        // Validate the UserRoles in the database
        List<UserRoles> userRolesList = userRolesRepository.findAll();
        assertThat(userRolesList).hasSize(databaseSizeBeforeCreate + 1);
        UserRoles testUserRoles = userRolesList.get(userRolesList.size() - 1);
        assertThat(testUserRoles.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUserRoles.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUserRoles.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the UserRoles in Elasticsearch
        verify(mockUserRolesSearchRepository, times(1)).save(testUserRoles);
    }

    @Test
    public void createUserRolesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRolesRepository.findAll().size();

        // Create the UserRoles with an existing ID
        userRoles.setId("existing_id");
        UserRolesDTO userRolesDTO = userRolesMapper.toDto(userRoles);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRolesMockMvc.perform(post("/api/user-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRolesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRoles in the database
        List<UserRoles> userRolesList = userRolesRepository.findAll();
        assertThat(userRolesList).hasSize(databaseSizeBeforeCreate);

        // Validate the UserRoles in Elasticsearch
        verify(mockUserRolesSearchRepository, times(0)).save(userRoles);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRolesRepository.findAll().size();
        // set the field null
        userRoles.setCode(null);

        // Create the UserRoles, which fails.
        UserRolesDTO userRolesDTO = userRolesMapper.toDto(userRoles);


        restUserRolesMockMvc.perform(post("/api/user-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRolesDTO)))
            .andExpect(status().isBadRequest());

        List<UserRoles> userRolesList = userRolesRepository.findAll();
        assertThat(userRolesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRolesRepository.findAll().size();
        // set the field null
        userRoles.setDescription(null);

        // Create the UserRoles, which fails.
        UserRolesDTO userRolesDTO = userRolesMapper.toDto(userRoles);


        restUserRolesMockMvc.perform(post("/api/user-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRolesDTO)))
            .andExpect(status().isBadRequest());

        List<UserRoles> userRolesList = userRolesRepository.findAll();
        assertThat(userRolesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllUserRoles() throws Exception {
        // Initialize the database
        userRolesRepository.save(userRoles);

        // Get all the userRolesList
        restUserRolesMockMvc.perform(get("/api/user-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRoles.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getUserRoles() throws Exception {
        // Initialize the database
        userRolesRepository.save(userRoles);

        // Get the userRoles
        restUserRolesMockMvc.perform(get("/api/user-roles/{id}", userRoles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userRoles.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    public void getNonExistingUserRoles() throws Exception {
        // Get the userRoles
        restUserRolesMockMvc.perform(get("/api/user-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserRoles() throws Exception {
        // Initialize the database
        userRolesRepository.save(userRoles);

        int databaseSizeBeforeUpdate = userRolesRepository.findAll().size();

        // Update the userRoles
        UserRoles updatedUserRoles = userRolesRepository.findById(userRoles.getId()).get();
        updatedUserRoles
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        UserRolesDTO userRolesDTO = userRolesMapper.toDto(updatedUserRoles);

        restUserRolesMockMvc.perform(put("/api/user-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRolesDTO)))
            .andExpect(status().isOk());

        // Validate the UserRoles in the database
        List<UserRoles> userRolesList = userRolesRepository.findAll();
        assertThat(userRolesList).hasSize(databaseSizeBeforeUpdate);
        UserRoles testUserRoles = userRolesList.get(userRolesList.size() - 1);
        assertThat(testUserRoles.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserRoles.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserRoles.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the UserRoles in Elasticsearch
        verify(mockUserRolesSearchRepository, times(1)).save(testUserRoles);
    }

    @Test
    public void updateNonExistingUserRoles() throws Exception {
        int databaseSizeBeforeUpdate = userRolesRepository.findAll().size();

        // Create the UserRoles
        UserRolesDTO userRolesDTO = userRolesMapper.toDto(userRoles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRolesMockMvc.perform(put("/api/user-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRolesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRoles in the database
        List<UserRoles> userRolesList = userRolesRepository.findAll();
        assertThat(userRolesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UserRoles in Elasticsearch
        verify(mockUserRolesSearchRepository, times(0)).save(userRoles);
    }

    @Test
    public void deleteUserRoles() throws Exception {
        // Initialize the database
        userRolesRepository.save(userRoles);

        int databaseSizeBeforeDelete = userRolesRepository.findAll().size();

        // Delete the userRoles
        restUserRolesMockMvc.perform(delete("/api/user-roles/{id}", userRoles.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRoles> userRolesList = userRolesRepository.findAll();
        assertThat(userRolesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UserRoles in Elasticsearch
        verify(mockUserRolesSearchRepository, times(1)).deleteById(userRoles.getId());
    }

    @Test
    public void searchUserRoles() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        userRolesRepository.save(userRoles);
        when(mockUserRolesSearchRepository.search(queryStringQuery("id:" + userRoles.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(userRoles), PageRequest.of(0, 1), 1));

        // Search the userRoles
        restUserRolesMockMvc.perform(get("/api/_search/user-roles?query=id:" + userRoles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRoles.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
