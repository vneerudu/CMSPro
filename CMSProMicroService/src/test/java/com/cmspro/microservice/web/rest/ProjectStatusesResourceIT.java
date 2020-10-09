package com.cmspro.microservice.web.rest;

import com.cmspro.microservice.CmsProMicroServiceApp;
import com.cmspro.microservice.domain.ProjectStatuses;
import com.cmspro.microservice.repository.ProjectStatusesRepository;
import com.cmspro.microservice.repository.search.ProjectStatusesSearchRepository;
import com.cmspro.microservice.service.ProjectStatusesService;

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
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProjectStatusesResource} REST controller.
 */
@SpringBootTest(classes = CmsProMicroServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProjectStatusesResourceIT {

    private static final Long DEFAULT_STATUS_ID = 1L;
    private static final Long UPDATED_STATUS_ID = 2L;

    private static final String DEFAULT_STATUS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private ProjectStatusesRepository projectStatusesRepository;

    @Autowired
    private ProjectStatusesService projectStatusesService;

    /**
     * This repository is mocked in the com.cmspro.microservice.repository.search test package.
     *
     * @see com.cmspro.microservice.repository.search.ProjectStatusesSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProjectStatusesSearchRepository mockProjectStatusesSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectStatusesMockMvc;

    private ProjectStatuses projectStatuses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectStatuses createEntity(EntityManager em) {
        ProjectStatuses projectStatuses = new ProjectStatuses()
            .status_id(DEFAULT_STATUS_ID)
            .status_code(DEFAULT_STATUS_CODE)
            .description(DEFAULT_DESCRIPTION)
            .is_active(DEFAULT_IS_ACTIVE);
        return projectStatuses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectStatuses createUpdatedEntity(EntityManager em) {
        ProjectStatuses projectStatuses = new ProjectStatuses()
            .status_id(UPDATED_STATUS_ID)
            .status_code(UPDATED_STATUS_CODE)
            .description(UPDATED_DESCRIPTION)
            .is_active(UPDATED_IS_ACTIVE);
        return projectStatuses;
    }

    @BeforeEach
    public void initTest() {
        projectStatuses = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectStatuses() throws Exception {
        int databaseSizeBeforeCreate = projectStatusesRepository.findAll().size();
        // Create the ProjectStatuses
        restProjectStatusesMockMvc.perform(post("/api/project-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectStatuses)))
            .andExpect(status().isCreated());

        // Validate the ProjectStatuses in the database
        List<ProjectStatuses> projectStatusesList = projectStatusesRepository.findAll();
        assertThat(projectStatusesList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectStatuses testProjectStatuses = projectStatusesList.get(projectStatusesList.size() - 1);
        assertThat(testProjectStatuses.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testProjectStatuses.getStatus_code()).isEqualTo(DEFAULT_STATUS_CODE);
        assertThat(testProjectStatuses.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectStatuses.isIs_active()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the ProjectStatuses in Elasticsearch
        verify(mockProjectStatusesSearchRepository, times(1)).save(testProjectStatuses);
    }

    @Test
    @Transactional
    public void createProjectStatusesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectStatusesRepository.findAll().size();

        // Create the ProjectStatuses with an existing ID
        projectStatuses.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectStatusesMockMvc.perform(post("/api/project-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectStatuses)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectStatuses in the database
        List<ProjectStatuses> projectStatusesList = projectStatusesRepository.findAll();
        assertThat(projectStatusesList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProjectStatuses in Elasticsearch
        verify(mockProjectStatusesSearchRepository, times(0)).save(projectStatuses);
    }


    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectStatusesRepository.findAll().size();
        // set the field null
        projectStatuses.setStatus_id(null);

        // Create the ProjectStatuses, which fails.


        restProjectStatusesMockMvc.perform(post("/api/project-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectStatuses)))
            .andExpect(status().isBadRequest());

        List<ProjectStatuses> projectStatusesList = projectStatusesRepository.findAll();
        assertThat(projectStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_codeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectStatusesRepository.findAll().size();
        // set the field null
        projectStatuses.setStatus_code(null);

        // Create the ProjectStatuses, which fails.


        restProjectStatusesMockMvc.perform(post("/api/project-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectStatuses)))
            .andExpect(status().isBadRequest());

        List<ProjectStatuses> projectStatusesList = projectStatusesRepository.findAll();
        assertThat(projectStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectStatusesRepository.findAll().size();
        // set the field null
        projectStatuses.setDescription(null);

        // Create the ProjectStatuses, which fails.


        restProjectStatusesMockMvc.perform(post("/api/project-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectStatuses)))
            .andExpect(status().isBadRequest());

        List<ProjectStatuses> projectStatusesList = projectStatusesRepository.findAll();
        assertThat(projectStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIs_activeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectStatusesRepository.findAll().size();
        // set the field null
        projectStatuses.setIs_active(null);

        // Create the ProjectStatuses, which fails.


        restProjectStatusesMockMvc.perform(post("/api/project-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectStatuses)))
            .andExpect(status().isBadRequest());

        List<ProjectStatuses> projectStatusesList = projectStatusesRepository.findAll();
        assertThat(projectStatusesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectStatuses() throws Exception {
        // Initialize the database
        projectStatusesRepository.saveAndFlush(projectStatuses);

        // Get all the projectStatusesList
        restProjectStatusesMockMvc.perform(get("/api/project-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectStatuses.getId().intValue())))
            .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID.intValue())))
            .andExpect(jsonPath("$.[*].status_code").value(hasItem(DEFAULT_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].is_active").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProjectStatuses() throws Exception {
        // Initialize the database
        projectStatusesRepository.saveAndFlush(projectStatuses);

        // Get the projectStatuses
        restProjectStatusesMockMvc.perform(get("/api/project-statuses/{id}", projectStatuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectStatuses.getId().intValue()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID.intValue()))
            .andExpect(jsonPath("$.status_code").value(DEFAULT_STATUS_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.is_active").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProjectStatuses() throws Exception {
        // Get the projectStatuses
        restProjectStatusesMockMvc.perform(get("/api/project-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectStatuses() throws Exception {
        // Initialize the database
        projectStatusesService.save(projectStatuses);

        int databaseSizeBeforeUpdate = projectStatusesRepository.findAll().size();

        // Update the projectStatuses
        ProjectStatuses updatedProjectStatuses = projectStatusesRepository.findById(projectStatuses.getId()).get();
        // Disconnect from session so that the updates on updatedProjectStatuses are not directly saved in db
        em.detach(updatedProjectStatuses);
        updatedProjectStatuses
            .status_id(UPDATED_STATUS_ID)
            .status_code(UPDATED_STATUS_CODE)
            .description(UPDATED_DESCRIPTION)
            .is_active(UPDATED_IS_ACTIVE);

        restProjectStatusesMockMvc.perform(put("/api/project-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectStatuses)))
            .andExpect(status().isOk());

        // Validate the ProjectStatuses in the database
        List<ProjectStatuses> projectStatusesList = projectStatusesRepository.findAll();
        assertThat(projectStatusesList).hasSize(databaseSizeBeforeUpdate);
        ProjectStatuses testProjectStatuses = projectStatusesList.get(projectStatusesList.size() - 1);
        assertThat(testProjectStatuses.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testProjectStatuses.getStatus_code()).isEqualTo(UPDATED_STATUS_CODE);
        assertThat(testProjectStatuses.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectStatuses.isIs_active()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the ProjectStatuses in Elasticsearch
        verify(mockProjectStatusesSearchRepository, times(2)).save(testProjectStatuses);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectStatuses() throws Exception {
        int databaseSizeBeforeUpdate = projectStatusesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectStatusesMockMvc.perform(put("/api/project-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectStatuses)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectStatuses in the database
        List<ProjectStatuses> projectStatusesList = projectStatusesRepository.findAll();
        assertThat(projectStatusesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProjectStatuses in Elasticsearch
        verify(mockProjectStatusesSearchRepository, times(0)).save(projectStatuses);
    }

    @Test
    @Transactional
    public void deleteProjectStatuses() throws Exception {
        // Initialize the database
        projectStatusesService.save(projectStatuses);

        int databaseSizeBeforeDelete = projectStatusesRepository.findAll().size();

        // Delete the projectStatuses
        restProjectStatusesMockMvc.perform(delete("/api/project-statuses/{id}", projectStatuses.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectStatuses> projectStatusesList = projectStatusesRepository.findAll();
        assertThat(projectStatusesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProjectStatuses in Elasticsearch
        verify(mockProjectStatusesSearchRepository, times(1)).deleteById(projectStatuses.getId());
    }

    @Test
    @Transactional
    public void searchProjectStatuses() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        projectStatusesService.save(projectStatuses);
        when(mockProjectStatusesSearchRepository.search(queryStringQuery("id:" + projectStatuses.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(projectStatuses), PageRequest.of(0, 1), 1));

        // Search the projectStatuses
        restProjectStatusesMockMvc.perform(get("/api/_search/project-statuses?query=id:" + projectStatuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectStatuses.getId().intValue())))
            .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID.intValue())))
            .andExpect(jsonPath("$.[*].status_code").value(hasItem(DEFAULT_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].is_active").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
