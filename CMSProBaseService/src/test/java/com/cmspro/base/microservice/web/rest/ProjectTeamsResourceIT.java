package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.ProjectTeams;
import com.cmspro.base.microservice.repository.ProjectTeamsRepository;
import com.cmspro.base.microservice.repository.search.ProjectTeamsSearchRepository;
import com.cmspro.base.microservice.service.ProjectTeamsService;
import com.cmspro.base.microservice.service.dto.ProjectTeamsDTO;
import com.cmspro.base.microservice.service.mapper.ProjectTeamsMapper;

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
 * Integration tests for the {@link ProjectTeamsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProjectTeamsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProjectTeamsRepository projectTeamsRepository;

    @Autowired
    private ProjectTeamsMapper projectTeamsMapper;

    @Autowired
    private ProjectTeamsService projectTeamsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.ProjectTeamsSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProjectTeamsSearchRepository mockProjectTeamsSearchRepository;

    @Autowired
    private MockMvc restProjectTeamsMockMvc;

    private ProjectTeams projectTeams;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTeams createEntity() {
        ProjectTeams projectTeams = new ProjectTeams()
            .name(DEFAULT_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return projectTeams;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTeams createUpdatedEntity() {
        ProjectTeams projectTeams = new ProjectTeams()
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return projectTeams;
    }

    @BeforeEach
    public void initTest() {
        projectTeamsRepository.deleteAll();
        projectTeams = createEntity();
    }

    @Test
    public void createProjectTeams() throws Exception {
        int databaseSizeBeforeCreate = projectTeamsRepository.findAll().size();
        // Create the ProjectTeams
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);
        restProjectTeamsMockMvc.perform(post("/api/project-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectTeams testProjectTeams = projectTeamsList.get(projectTeamsList.size() - 1);
        assertThat(testProjectTeams.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectTeams.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProjectTeams.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the ProjectTeams in Elasticsearch
        verify(mockProjectTeamsSearchRepository, times(1)).save(testProjectTeams);
    }

    @Test
    public void createProjectTeamsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectTeamsRepository.findAll().size();

        // Create the ProjectTeams with an existing ID
        projectTeams.setId("existing_id");
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectTeamsMockMvc.perform(post("/api/project-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProjectTeams in Elasticsearch
        verify(mockProjectTeamsSearchRepository, times(0)).save(projectTeams);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTeamsRepository.findAll().size();
        // set the field null
        projectTeams.setName(null);

        // Create the ProjectTeams, which fails.
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);


        restProjectTeamsMockMvc.perform(post("/api/project-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTeamsRepository.findAll().size();
        // set the field null
        projectTeams.setCreatedBy(null);

        // Create the ProjectTeams, which fails.
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);


        restProjectTeamsMockMvc.perform(post("/api/project-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTeamsRepository.findAll().size();
        // set the field null
        projectTeams.setCreationDate(null);

        // Create the ProjectTeams, which fails.
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);


        restProjectTeamsMockMvc.perform(post("/api/project-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProjectTeams() throws Exception {
        // Initialize the database
        projectTeamsRepository.save(projectTeams);

        // Get all the projectTeamsList
        restProjectTeamsMockMvc.perform(get("/api/project-teams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTeams.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getProjectTeams() throws Exception {
        // Initialize the database
        projectTeamsRepository.save(projectTeams);

        // Get the projectTeams
        restProjectTeamsMockMvc.perform(get("/api/project-teams/{id}", projectTeams.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectTeams.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingProjectTeams() throws Exception {
        // Get the projectTeams
        restProjectTeamsMockMvc.perform(get("/api/project-teams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProjectTeams() throws Exception {
        // Initialize the database
        projectTeamsRepository.save(projectTeams);

        int databaseSizeBeforeUpdate = projectTeamsRepository.findAll().size();

        // Update the projectTeams
        ProjectTeams updatedProjectTeams = projectTeamsRepository.findById(projectTeams.getId()).get();
        updatedProjectTeams
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(updatedProjectTeams);

        restProjectTeamsMockMvc.perform(put("/api/project-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO)))
            .andExpect(status().isOk());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeUpdate);
        ProjectTeams testProjectTeams = projectTeamsList.get(projectTeamsList.size() - 1);
        assertThat(testProjectTeams.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectTeams.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjectTeams.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the ProjectTeams in Elasticsearch
        verify(mockProjectTeamsSearchRepository, times(1)).save(testProjectTeams);
    }

    @Test
    public void updateNonExistingProjectTeams() throws Exception {
        int databaseSizeBeforeUpdate = projectTeamsRepository.findAll().size();

        // Create the ProjectTeams
        ProjectTeamsDTO projectTeamsDTO = projectTeamsMapper.toDto(projectTeams);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectTeamsMockMvc.perform(put("/api/project-teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectTeamsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTeams in the database
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProjectTeams in Elasticsearch
        verify(mockProjectTeamsSearchRepository, times(0)).save(projectTeams);
    }

    @Test
    public void deleteProjectTeams() throws Exception {
        // Initialize the database
        projectTeamsRepository.save(projectTeams);

        int databaseSizeBeforeDelete = projectTeamsRepository.findAll().size();

        // Delete the projectTeams
        restProjectTeamsMockMvc.perform(delete("/api/project-teams/{id}", projectTeams.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectTeams> projectTeamsList = projectTeamsRepository.findAll();
        assertThat(projectTeamsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProjectTeams in Elasticsearch
        verify(mockProjectTeamsSearchRepository, times(1)).deleteById(projectTeams.getId());
    }

    @Test
    public void searchProjectTeams() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        projectTeamsRepository.save(projectTeams);
        when(mockProjectTeamsSearchRepository.search(queryStringQuery("id:" + projectTeams.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(projectTeams), PageRequest.of(0, 1), 1));

        // Search the projectTeams
        restProjectTeamsMockMvc.perform(get("/api/_search/project-teams?query=id:" + projectTeams.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTeams.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
