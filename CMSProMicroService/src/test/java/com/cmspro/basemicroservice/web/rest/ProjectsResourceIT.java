package com.cmspro.basemicroservice.web.rest;

import com.cmspro.basemicroservice.CmsProMicroServiceApp;
import com.cmspro.basemicroservice.domain.Projects;
import com.cmspro.basemicroservice.domain.ProjectStatus;
import com.cmspro.basemicroservice.repository.ProjectsRepository;
import com.cmspro.basemicroservice.repository.search.ProjectsSearchRepository;
import com.cmspro.basemicroservice.service.ProjectsService;
import com.cmspro.basemicroservice.service.dto.ProjectsDTO;
import com.cmspro.basemicroservice.service.mapper.ProjectsMapper;
import com.cmspro.basemicroservice.web.rest.errors.ExceptionTranslator;
import com.cmspro.basemicroservice.service.dto.ProjectsCriteria;
import com.cmspro.basemicroservice.service.ProjectsQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.cmspro.basemicroservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ProjectsResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = CmsProMicroServiceApp.class)
public class ProjectsResourceIT {

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FINISH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISH_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private ProjectsMapper projectsMapper;

    @Autowired
    private ProjectsService projectsService;

    /**
     * This repository is mocked in the com.cmspro.basemicroservice.repository.search test package.
     *
     * @see com.cmspro.basemicroservice.repository.search.ProjectsSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProjectsSearchRepository mockProjectsSearchRepository;

    @Autowired
    private ProjectsQueryService projectsQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProjectsMockMvc;

    private Projects projects;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectsResource projectsResource = new ProjectsResource(projectsService, projectsQueryService);
        this.restProjectsMockMvc = MockMvcBuilders.standaloneSetup(projectsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projects createEntity(EntityManager em) {
        Projects projects = new Projects()
            .projectID(DEFAULT_PROJECT_ID)
            .name(DEFAULT_NAME)
            .department(DEFAULT_DEPARTMENT)
            .organization(DEFAULT_ORGANIZATION)
            .startDate(DEFAULT_START_DATE)
            .finishDate(DEFAULT_FINISH_DATE);
        return projects;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projects createUpdatedEntity(EntityManager em) {
        Projects projects = new Projects()
            .projectID(UPDATED_PROJECT_ID)
            .name(UPDATED_NAME)
            .department(UPDATED_DEPARTMENT)
            .organization(UPDATED_ORGANIZATION)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE);
        return projects;
    }

    @BeforeEach
    public void initTest() {
        projects = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjects() throws Exception {
        int databaseSizeBeforeCreate = projectsRepository.findAll().size();

        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);
        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isCreated());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeCreate + 1);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getProjectID()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testProjects.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjects.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testProjects.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testProjects.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProjects.getFinishDate()).isEqualTo(DEFAULT_FINISH_DATE);

        // Validate the Projects in Elasticsearch
        verify(mockProjectsSearchRepository, times(1)).save(testProjects);
    }

    @Test
    @Transactional
    public void createProjectsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectsRepository.findAll().size();

        // Create the Projects with an existing ID
        projects.setId(1L);
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Projects in Elasticsearch
        verify(mockProjectsSearchRepository, times(0)).save(projects);
    }


    @Test
    @Transactional
    public void checkProjectIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectsRepository.findAll().size();
        // set the field null
        projects.setProjectID(null);

        // Create the Projects, which fails.
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectsRepository.findAll().size();
        // set the field null
        projects.setName(null);

        // Create the Projects, which fails.
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectsRepository.findAll().size();
        // set the field null
        projects.setStartDate(null);

        // Create the Projects, which fails.
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList
        restProjectsMockMvc.perform(get("/api/projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projects.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectID").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].finishDate").value(hasItem(DEFAULT_FINISH_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get the projects
        restProjectsMockMvc.perform(get("/api/projects/{id}", projects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projects.getId().intValue()))
            .andExpect(jsonPath("$.projectID").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.finishDate").value(DEFAULT_FINISH_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectsByProjectIDIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectID equals to DEFAULT_PROJECT_ID
        defaultProjectsShouldBeFound("projectID.equals=" + DEFAULT_PROJECT_ID);

        // Get all the projectsList where projectID equals to UPDATED_PROJECT_ID
        defaultProjectsShouldNotBeFound("projectID.equals=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllProjectsByProjectIDIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectID in DEFAULT_PROJECT_ID or UPDATED_PROJECT_ID
        defaultProjectsShouldBeFound("projectID.in=" + DEFAULT_PROJECT_ID + "," + UPDATED_PROJECT_ID);

        // Get all the projectsList where projectID equals to UPDATED_PROJECT_ID
        defaultProjectsShouldNotBeFound("projectID.in=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllProjectsByProjectIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectID is not null
        defaultProjectsShouldBeFound("projectID.specified=true");

        // Get all the projectsList where projectID is null
        defaultProjectsShouldNotBeFound("projectID.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectsByProjectIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectID greater than or equals to DEFAULT_PROJECT_ID
        defaultProjectsShouldBeFound("projectID.greaterOrEqualThan=" + DEFAULT_PROJECT_ID);

        // Get all the projectsList where projectID greater than or equals to UPDATED_PROJECT_ID
        defaultProjectsShouldNotBeFound("projectID.greaterOrEqualThan=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllProjectsByProjectIDIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where projectID less than or equals to DEFAULT_PROJECT_ID
        defaultProjectsShouldNotBeFound("projectID.lessThan=" + DEFAULT_PROJECT_ID);

        // Get all the projectsList where projectID less than or equals to UPDATED_PROJECT_ID
        defaultProjectsShouldBeFound("projectID.lessThan=" + UPDATED_PROJECT_ID);
    }


    @Test
    @Transactional
    public void getAllProjectsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where name equals to DEFAULT_NAME
        defaultProjectsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectsList where name equals to UPDATED_NAME
        defaultProjectsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectsList where name equals to UPDATED_NAME
        defaultProjectsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where name is not null
        defaultProjectsShouldBeFound("name.specified=true");

        // Get all the projectsList where name is null
        defaultProjectsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectsByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where department equals to DEFAULT_DEPARTMENT
        defaultProjectsShouldBeFound("department.equals=" + DEFAULT_DEPARTMENT);

        // Get all the projectsList where department equals to UPDATED_DEPARTMENT
        defaultProjectsShouldNotBeFound("department.equals=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    public void getAllProjectsByDepartmentIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where department in DEFAULT_DEPARTMENT or UPDATED_DEPARTMENT
        defaultProjectsShouldBeFound("department.in=" + DEFAULT_DEPARTMENT + "," + UPDATED_DEPARTMENT);

        // Get all the projectsList where department equals to UPDATED_DEPARTMENT
        defaultProjectsShouldNotBeFound("department.in=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    public void getAllProjectsByDepartmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where department is not null
        defaultProjectsShouldBeFound("department.specified=true");

        // Get all the projectsList where department is null
        defaultProjectsShouldNotBeFound("department.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectsByOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where organization equals to DEFAULT_ORGANIZATION
        defaultProjectsShouldBeFound("organization.equals=" + DEFAULT_ORGANIZATION);

        // Get all the projectsList where organization equals to UPDATED_ORGANIZATION
        defaultProjectsShouldNotBeFound("organization.equals=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllProjectsByOrganizationIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where organization in DEFAULT_ORGANIZATION or UPDATED_ORGANIZATION
        defaultProjectsShouldBeFound("organization.in=" + DEFAULT_ORGANIZATION + "," + UPDATED_ORGANIZATION);

        // Get all the projectsList where organization equals to UPDATED_ORGANIZATION
        defaultProjectsShouldNotBeFound("organization.in=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllProjectsByOrganizationIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where organization is not null
        defaultProjectsShouldBeFound("organization.specified=true");

        // Get all the projectsList where organization is null
        defaultProjectsShouldNotBeFound("organization.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where startDate equals to DEFAULT_START_DATE
        defaultProjectsShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the projectsList where startDate equals to UPDATED_START_DATE
        defaultProjectsShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProjectsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultProjectsShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the projectsList where startDate equals to UPDATED_START_DATE
        defaultProjectsShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProjectsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where startDate is not null
        defaultProjectsShouldBeFound("startDate.specified=true");

        // Get all the projectsList where startDate is null
        defaultProjectsShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where startDate greater than or equals to DEFAULT_START_DATE
        defaultProjectsShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the projectsList where startDate greater than or equals to UPDATED_START_DATE
        defaultProjectsShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProjectsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where startDate less than or equals to DEFAULT_START_DATE
        defaultProjectsShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the projectsList where startDate less than or equals to UPDATED_START_DATE
        defaultProjectsShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllProjectsByFinishDateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where finishDate equals to DEFAULT_FINISH_DATE
        defaultProjectsShouldBeFound("finishDate.equals=" + DEFAULT_FINISH_DATE);

        // Get all the projectsList where finishDate equals to UPDATED_FINISH_DATE
        defaultProjectsShouldNotBeFound("finishDate.equals=" + UPDATED_FINISH_DATE);
    }

    @Test
    @Transactional
    public void getAllProjectsByFinishDateIsInShouldWork() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where finishDate in DEFAULT_FINISH_DATE or UPDATED_FINISH_DATE
        defaultProjectsShouldBeFound("finishDate.in=" + DEFAULT_FINISH_DATE + "," + UPDATED_FINISH_DATE);

        // Get all the projectsList where finishDate equals to UPDATED_FINISH_DATE
        defaultProjectsShouldNotBeFound("finishDate.in=" + UPDATED_FINISH_DATE);
    }

    @Test
    @Transactional
    public void getAllProjectsByFinishDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where finishDate is not null
        defaultProjectsShouldBeFound("finishDate.specified=true");

        // Get all the projectsList where finishDate is null
        defaultProjectsShouldNotBeFound("finishDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectsByFinishDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where finishDate greater than or equals to DEFAULT_FINISH_DATE
        defaultProjectsShouldBeFound("finishDate.greaterOrEqualThan=" + DEFAULT_FINISH_DATE);

        // Get all the projectsList where finishDate greater than or equals to UPDATED_FINISH_DATE
        defaultProjectsShouldNotBeFound("finishDate.greaterOrEqualThan=" + UPDATED_FINISH_DATE);
    }

    @Test
    @Transactional
    public void getAllProjectsByFinishDateIsLessThanSomething() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList where finishDate less than or equals to DEFAULT_FINISH_DATE
        defaultProjectsShouldNotBeFound("finishDate.lessThan=" + DEFAULT_FINISH_DATE);

        // Get all the projectsList where finishDate less than or equals to UPDATED_FINISH_DATE
        defaultProjectsShouldBeFound("finishDate.lessThan=" + UPDATED_FINISH_DATE);
    }


    @Test
    @Transactional
    public void getAllProjectsByProjectStatusRelIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectStatus projectStatusRel = ProjectStatusResourceIT.createEntity(em);
        em.persist(projectStatusRel);
        em.flush();
        projects.setProjectStatusRel(projectStatusRel);
        projectsRepository.saveAndFlush(projects);
        Long projectStatusRelId = projectStatusRel.getId();

        // Get all the projectsList where projectStatusRel equals to projectStatusRelId
        defaultProjectsShouldBeFound("projectStatusRelId.equals=" + projectStatusRelId);

        // Get all the projectsList where projectStatusRel equals to projectStatusRelId + 1
        defaultProjectsShouldNotBeFound("projectStatusRelId.equals=" + (projectStatusRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectsShouldBeFound(String filter) throws Exception {
        restProjectsMockMvc.perform(get("/api/projects?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projects.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectID").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].finishDate").value(hasItem(DEFAULT_FINISH_DATE.toString())));

        // Check, that the count call also returns 1
        restProjectsMockMvc.perform(get("/api/projects/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectsShouldNotBeFound(String filter) throws Exception {
        restProjectsMockMvc.perform(get("/api/projects?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectsMockMvc.perform(get("/api/projects/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjects() throws Exception {
        // Get the projects
        restProjectsMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Update the projects
        Projects updatedProjects = projectsRepository.findById(projects.getId()).get();
        // Disconnect from session so that the updates on updatedProjects are not directly saved in db
        em.detach(updatedProjects);
        updatedProjects
            .projectID(UPDATED_PROJECT_ID)
            .name(UPDATED_NAME)
            .department(UPDATED_DEPARTMENT)
            .organization(UPDATED_ORGANIZATION)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE);
        ProjectsDTO projectsDTO = projectsMapper.toDto(updatedProjects);

        restProjectsMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isOk());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getProjectID()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testProjects.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjects.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testProjects.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testProjects.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProjects.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);

        // Validate the Projects in Elasticsearch
        verify(mockProjectsSearchRepository, times(1)).save(testProjects);
    }

    @Test
    @Transactional
    public void updateNonExistingProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectsMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Projects in Elasticsearch
        verify(mockProjectsSearchRepository, times(0)).save(projects);
    }

    @Test
    @Transactional
    public void deleteProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        int databaseSizeBeforeDelete = projectsRepository.findAll().size();

        // Delete the projects
        restProjectsMockMvc.perform(delete("/api/projects/{id}", projects.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Projects in Elasticsearch
        verify(mockProjectsSearchRepository, times(1)).deleteById(projects.getId());
    }

    @Test
    @Transactional
    public void searchProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);
        when(mockProjectsSearchRepository.search(queryStringQuery("id:" + projects.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(projects), PageRequest.of(0, 1), 1));
        // Search the projects
        restProjectsMockMvc.perform(get("/api/_search/projects?query=id:" + projects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projects.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectID").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].finishDate").value(hasItem(DEFAULT_FINISH_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projects.class);
        Projects projects1 = new Projects();
        projects1.setId(1L);
        Projects projects2 = new Projects();
        projects2.setId(projects1.getId());
        assertThat(projects1).isEqualTo(projects2);
        projects2.setId(2L);
        assertThat(projects1).isNotEqualTo(projects2);
        projects1.setId(null);
        assertThat(projects1).isNotEqualTo(projects2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectsDTO.class);
        ProjectsDTO projectsDTO1 = new ProjectsDTO();
        projectsDTO1.setId(1L);
        ProjectsDTO projectsDTO2 = new ProjectsDTO();
        assertThat(projectsDTO1).isNotEqualTo(projectsDTO2);
        projectsDTO2.setId(projectsDTO1.getId());
        assertThat(projectsDTO1).isEqualTo(projectsDTO2);
        projectsDTO2.setId(2L);
        assertThat(projectsDTO1).isNotEqualTo(projectsDTO2);
        projectsDTO1.setId(null);
        assertThat(projectsDTO1).isNotEqualTo(projectsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(projectsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(projectsMapper.fromId(null)).isNull();
    }
}
