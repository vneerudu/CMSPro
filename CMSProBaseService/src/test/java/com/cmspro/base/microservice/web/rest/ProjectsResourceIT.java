package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.Projects;
import com.cmspro.base.microservice.repository.ProjectsRepository;
import com.cmspro.base.microservice.repository.search.ProjectsSearchRepository;
import com.cmspro.base.microservice.service.ProjectsService;
import com.cmspro.base.microservice.service.dto.ProjectsDTO;
import com.cmspro.base.microservice.service.mapper.ProjectsMapper;

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
 * Integration tests for the {@link ProjectsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProjectsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FINISH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private ProjectsMapper projectsMapper;

    @Autowired
    private ProjectsService projectsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.ProjectsSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProjectsSearchRepository mockProjectsSearchRepository;

    @Autowired
    private MockMvc restProjectsMockMvc;

    private Projects projects;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projects createEntity() {
        Projects projects = new Projects()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .finishDate(DEFAULT_FINISH_DATE)
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return projects;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projects createUpdatedEntity() {
        Projects projects = new Projects()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return projects;
    }

    @BeforeEach
    public void initTest() {
        projectsRepository.deleteAll();
        projects = createEntity();
    }

    @Test
    public void createProjects() throws Exception {
        int databaseSizeBeforeCreate = projectsRepository.findAll().size();
        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);
        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isCreated());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeCreate + 1);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjects.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjects.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProjects.getFinishDate()).isEqualTo(DEFAULT_FINISH_DATE);
        assertThat(testProjects.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testProjects.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProjects.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the Projects in Elasticsearch
        verify(mockProjectsSearchRepository, times(1)).save(testProjects);
    }

    @Test
    public void createProjectsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectsRepository.findAll().size();

        // Create the Projects with an existing ID
        projects.setId("existing_id");
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Projects in Elasticsearch
        verify(mockProjectsSearchRepository, times(0)).save(projects);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectsRepository.findAll().size();
        // set the field null
        projects.setName(null);

        // Create the Projects, which fails.
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);


        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectsRepository.findAll().size();
        // set the field null
        projects.setCreatedBy(null);

        // Create the Projects, which fails.
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);


        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectsRepository.findAll().size();
        // set the field null
        projects.setCreationDate(null);

        // Create the Projects, which fails.
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);


        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectsRepository.save(projects);

        // Get all the projectsList
        restProjectsMockMvc.perform(get("/api/projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projects.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].finishDate").value(hasItem(DEFAULT_FINISH_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getProjects() throws Exception {
        // Initialize the database
        projectsRepository.save(projects);

        // Get the projects
        restProjectsMockMvc.perform(get("/api/projects/{id}", projects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projects.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.finishDate").value(DEFAULT_FINISH_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdate").value(DEFAULT_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingProjects() throws Exception {
        // Get the projects
        restProjectsMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProjects() throws Exception {
        // Initialize the database
        projectsRepository.save(projects);

        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Update the projects
        Projects updatedProjects = projectsRepository.findById(projects.getId()).get();
        updatedProjects
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        ProjectsDTO projectsDTO = projectsMapper.toDto(updatedProjects);

        restProjectsMockMvc.perform(put("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isOk());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjects.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjects.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProjects.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testProjects.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testProjects.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjects.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the Projects in Elasticsearch
        verify(mockProjectsSearchRepository, times(1)).save(testProjects);
    }

    @Test
    public void updateNonExistingProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectsMockMvc.perform(put("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Projects in Elasticsearch
        verify(mockProjectsSearchRepository, times(0)).save(projects);
    }

    @Test
    public void deleteProjects() throws Exception {
        // Initialize the database
        projectsRepository.save(projects);

        int databaseSizeBeforeDelete = projectsRepository.findAll().size();

        // Delete the projects
        restProjectsMockMvc.perform(delete("/api/projects/{id}", projects.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Projects in Elasticsearch
        verify(mockProjectsSearchRepository, times(1)).deleteById(projects.getId());
    }

    @Test
    public void searchProjects() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        projectsRepository.save(projects);
        when(mockProjectsSearchRepository.search(queryStringQuery("id:" + projects.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(projects), PageRequest.of(0, 1), 1));

        // Search the projects
        restProjectsMockMvc.perform(get("/api/_search/projects?query=id:" + projects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projects.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].finishDate").value(hasItem(DEFAULT_FINISH_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
