package com.cmspro.basemicroservice.web.rest;

import com.cmspro.basemicroservice.CmsProMicroServiceApp;
import com.cmspro.basemicroservice.domain.ProjectStatus;
import com.cmspro.basemicroservice.repository.ProjectStatusRepository;
import com.cmspro.basemicroservice.repository.search.ProjectStatusSearchRepository;
import com.cmspro.basemicroservice.service.ProjectStatusService;
import com.cmspro.basemicroservice.service.dto.ProjectStatusDTO;
import com.cmspro.basemicroservice.service.mapper.ProjectStatusMapper;
import com.cmspro.basemicroservice.web.rest.errors.ExceptionTranslator;
import com.cmspro.basemicroservice.service.dto.ProjectStatusCriteria;
import com.cmspro.basemicroservice.service.ProjectStatusQueryService;

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
 * Integration tests for the {@Link ProjectStatusResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = CmsProMicroServiceApp.class)
public class ProjectStatusResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private ProjectStatusRepository projectStatusRepository;

    @Autowired
    private ProjectStatusMapper projectStatusMapper;

    @Autowired
    private ProjectStatusService projectStatusService;

    /**
     * This repository is mocked in the com.cmspro.basemicroservice.repository.search test package.
     *
     * @see com.cmspro.basemicroservice.repository.search.ProjectStatusSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProjectStatusSearchRepository mockProjectStatusSearchRepository;

    @Autowired
    private ProjectStatusQueryService projectStatusQueryService;

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

    private MockMvc restProjectStatusMockMvc;

    private ProjectStatus projectStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectStatusResource projectStatusResource = new ProjectStatusResource(projectStatusService, projectStatusQueryService);
        this.restProjectStatusMockMvc = MockMvcBuilders.standaloneSetup(projectStatusResource)
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
    public static ProjectStatus createEntity(EntityManager em) {
        ProjectStatus projectStatus = new ProjectStatus()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return projectStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectStatus createUpdatedEntity(EntityManager em) {
        ProjectStatus projectStatus = new ProjectStatus()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return projectStatus;
    }

    @BeforeEach
    public void initTest() {
        projectStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectStatus() throws Exception {
        int databaseSizeBeforeCreate = projectStatusRepository.findAll().size();

        // Create the ProjectStatus
        ProjectStatusDTO projectStatusDTO = projectStatusMapper.toDto(projectStatus);
        restProjectStatusMockMvc.perform(post("/api/project-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectStatus in the database
        List<ProjectStatus> projectStatusList = projectStatusRepository.findAll();
        assertThat(projectStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectStatus testProjectStatus = projectStatusList.get(projectStatusList.size() - 1);
        assertThat(testProjectStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectStatus.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the ProjectStatus in Elasticsearch
        verify(mockProjectStatusSearchRepository, times(1)).save(testProjectStatus);
    }

    @Test
    @Transactional
    public void createProjectStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectStatusRepository.findAll().size();

        // Create the ProjectStatus with an existing ID
        projectStatus.setId(1L);
        ProjectStatusDTO projectStatusDTO = projectStatusMapper.toDto(projectStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectStatusMockMvc.perform(post("/api/project-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectStatus in the database
        List<ProjectStatus> projectStatusList = projectStatusRepository.findAll();
        assertThat(projectStatusList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProjectStatus in Elasticsearch
        verify(mockProjectStatusSearchRepository, times(0)).save(projectStatus);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectStatusRepository.findAll().size();
        // set the field null
        projectStatus.setCode(null);

        // Create the ProjectStatus, which fails.
        ProjectStatusDTO projectStatusDTO = projectStatusMapper.toDto(projectStatus);

        restProjectStatusMockMvc.perform(post("/api/project-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectStatusDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectStatus> projectStatusList = projectStatusRepository.findAll();
        assertThat(projectStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectStatusRepository.findAll().size();
        // set the field null
        projectStatus.setDescription(null);

        // Create the ProjectStatus, which fails.
        ProjectStatusDTO projectStatusDTO = projectStatusMapper.toDto(projectStatus);

        restProjectStatusMockMvc.perform(post("/api/project-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectStatusDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectStatus> projectStatusList = projectStatusRepository.findAll();
        assertThat(projectStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectStatusRepository.findAll().size();
        // set the field null
        projectStatus.setIsActive(null);

        // Create the ProjectStatus, which fails.
        ProjectStatusDTO projectStatusDTO = projectStatusMapper.toDto(projectStatus);

        restProjectStatusMockMvc.perform(post("/api/project-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectStatusDTO)))
            .andExpect(status().isBadRequest());

        List<ProjectStatus> projectStatusList = projectStatusRepository.findAll();
        assertThat(projectStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectStatuses() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        // Get all the projectStatusList
        restProjectStatusMockMvc.perform(get("/api/project-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProjectStatus() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        // Get the projectStatus
        restProjectStatusMockMvc.perform(get("/api/project-statuses/{id}", projectStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllProjectStatusesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        // Get all the projectStatusList where code equals to DEFAULT_CODE
        defaultProjectStatusShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectStatusList where code equals to UPDATED_CODE
        defaultProjectStatusShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectStatusesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        // Get all the projectStatusList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectStatusShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectStatusList where code equals to UPDATED_CODE
        defaultProjectStatusShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectStatusesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        // Get all the projectStatusList where code is not null
        defaultProjectStatusShouldBeFound("code.specified=true");

        // Get all the projectStatusList where code is null
        defaultProjectStatusShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectStatusesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        // Get all the projectStatusList where description equals to DEFAULT_DESCRIPTION
        defaultProjectStatusShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectStatusList where description equals to UPDATED_DESCRIPTION
        defaultProjectStatusShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectStatusesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        // Get all the projectStatusList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectStatusShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectStatusList where description equals to UPDATED_DESCRIPTION
        defaultProjectStatusShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectStatusesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        // Get all the projectStatusList where description is not null
        defaultProjectStatusShouldBeFound("description.specified=true");

        // Get all the projectStatusList where description is null
        defaultProjectStatusShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectStatusesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        // Get all the projectStatusList where isActive equals to DEFAULT_IS_ACTIVE
        defaultProjectStatusShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the projectStatusList where isActive equals to UPDATED_IS_ACTIVE
        defaultProjectStatusShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllProjectStatusesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        // Get all the projectStatusList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultProjectStatusShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the projectStatusList where isActive equals to UPDATED_IS_ACTIVE
        defaultProjectStatusShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllProjectStatusesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        // Get all the projectStatusList where isActive is not null
        defaultProjectStatusShouldBeFound("isActive.specified=true");

        // Get all the projectStatusList where isActive is null
        defaultProjectStatusShouldNotBeFound("isActive.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectStatusShouldBeFound(String filter) throws Exception {
        restProjectStatusMockMvc.perform(get("/api/project-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restProjectStatusMockMvc.perform(get("/api/project-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectStatusShouldNotBeFound(String filter) throws Exception {
        restProjectStatusMockMvc.perform(get("/api/project-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectStatusMockMvc.perform(get("/api/project-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectStatus() throws Exception {
        // Get the projectStatus
        restProjectStatusMockMvc.perform(get("/api/project-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectStatus() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        int databaseSizeBeforeUpdate = projectStatusRepository.findAll().size();

        // Update the projectStatus
        ProjectStatus updatedProjectStatus = projectStatusRepository.findById(projectStatus.getId()).get();
        // Disconnect from session so that the updates on updatedProjectStatus are not directly saved in db
        em.detach(updatedProjectStatus);
        updatedProjectStatus
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        ProjectStatusDTO projectStatusDTO = projectStatusMapper.toDto(updatedProjectStatus);

        restProjectStatusMockMvc.perform(put("/api/project-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectStatusDTO)))
            .andExpect(status().isOk());

        // Validate the ProjectStatus in the database
        List<ProjectStatus> projectStatusList = projectStatusRepository.findAll();
        assertThat(projectStatusList).hasSize(databaseSizeBeforeUpdate);
        ProjectStatus testProjectStatus = projectStatusList.get(projectStatusList.size() - 1);
        assertThat(testProjectStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectStatus.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the ProjectStatus in Elasticsearch
        verify(mockProjectStatusSearchRepository, times(1)).save(testProjectStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectStatus() throws Exception {
        int databaseSizeBeforeUpdate = projectStatusRepository.findAll().size();

        // Create the ProjectStatus
        ProjectStatusDTO projectStatusDTO = projectStatusMapper.toDto(projectStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectStatusMockMvc.perform(put("/api/project-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectStatus in the database
        List<ProjectStatus> projectStatusList = projectStatusRepository.findAll();
        assertThat(projectStatusList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProjectStatus in Elasticsearch
        verify(mockProjectStatusSearchRepository, times(0)).save(projectStatus);
    }

    @Test
    @Transactional
    public void deleteProjectStatus() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);

        int databaseSizeBeforeDelete = projectStatusRepository.findAll().size();

        // Delete the projectStatus
        restProjectStatusMockMvc.perform(delete("/api/project-statuses/{id}", projectStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectStatus> projectStatusList = projectStatusRepository.findAll();
        assertThat(projectStatusList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProjectStatus in Elasticsearch
        verify(mockProjectStatusSearchRepository, times(1)).deleteById(projectStatus.getId());
    }

    @Test
    @Transactional
    public void searchProjectStatus() throws Exception {
        // Initialize the database
        projectStatusRepository.saveAndFlush(projectStatus);
        when(mockProjectStatusSearchRepository.search(queryStringQuery("id:" + projectStatus.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(projectStatus), PageRequest.of(0, 1), 1));
        // Search the projectStatus
        restProjectStatusMockMvc.perform(get("/api/_search/project-statuses?query=id:" + projectStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectStatus.class);
        ProjectStatus projectStatus1 = new ProjectStatus();
        projectStatus1.setId(1L);
        ProjectStatus projectStatus2 = new ProjectStatus();
        projectStatus2.setId(projectStatus1.getId());
        assertThat(projectStatus1).isEqualTo(projectStatus2);
        projectStatus2.setId(2L);
        assertThat(projectStatus1).isNotEqualTo(projectStatus2);
        projectStatus1.setId(null);
        assertThat(projectStatus1).isNotEqualTo(projectStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectStatusDTO.class);
        ProjectStatusDTO projectStatusDTO1 = new ProjectStatusDTO();
        projectStatusDTO1.setId(1L);
        ProjectStatusDTO projectStatusDTO2 = new ProjectStatusDTO();
        assertThat(projectStatusDTO1).isNotEqualTo(projectStatusDTO2);
        projectStatusDTO2.setId(projectStatusDTO1.getId());
        assertThat(projectStatusDTO1).isEqualTo(projectStatusDTO2);
        projectStatusDTO2.setId(2L);
        assertThat(projectStatusDTO1).isNotEqualTo(projectStatusDTO2);
        projectStatusDTO1.setId(null);
        assertThat(projectStatusDTO1).isNotEqualTo(projectStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(projectStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(projectStatusMapper.fromId(null)).isNull();
    }
}
