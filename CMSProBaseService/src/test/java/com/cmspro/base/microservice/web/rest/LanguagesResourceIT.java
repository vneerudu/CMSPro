package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.Languages;
import com.cmspro.base.microservice.repository.LanguagesRepository;
import com.cmspro.base.microservice.repository.search.LanguagesSearchRepository;
import com.cmspro.base.microservice.service.LanguagesService;
import com.cmspro.base.microservice.service.dto.LanguagesDTO;
import com.cmspro.base.microservice.service.mapper.LanguagesMapper;

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
 * Integration tests for the {@link LanguagesResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LanguagesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private LanguagesRepository languagesRepository;

    @Autowired
    private LanguagesMapper languagesMapper;

    @Autowired
    private LanguagesService languagesService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.LanguagesSearchRepositoryMockConfiguration
     */
    @Autowired
    private LanguagesSearchRepository mockLanguagesSearchRepository;

    @Autowired
    private MockMvc restLanguagesMockMvc;

    private Languages languages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Languages createEntity() {
        Languages languages = new Languages()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return languages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Languages createUpdatedEntity() {
        Languages languages = new Languages()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return languages;
    }

    @BeforeEach
    public void initTest() {
        languagesRepository.deleteAll();
        languages = createEntity();
    }

    @Test
    public void createLanguages() throws Exception {
        int databaseSizeBeforeCreate = languagesRepository.findAll().size();
        // Create the Languages
        LanguagesDTO languagesDTO = languagesMapper.toDto(languages);
        restLanguagesMockMvc.perform(post("/api/languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(languagesDTO)))
            .andExpect(status().isCreated());

        // Validate the Languages in the database
        List<Languages> languagesList = languagesRepository.findAll();
        assertThat(languagesList).hasSize(databaseSizeBeforeCreate + 1);
        Languages testLanguages = languagesList.get(languagesList.size() - 1);
        assertThat(testLanguages.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLanguages.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Languages in Elasticsearch
        verify(mockLanguagesSearchRepository, times(1)).save(testLanguages);
    }

    @Test
    public void createLanguagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = languagesRepository.findAll().size();

        // Create the Languages with an existing ID
        languages.setId("existing_id");
        LanguagesDTO languagesDTO = languagesMapper.toDto(languages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLanguagesMockMvc.perform(post("/api/languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(languagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Languages in the database
        List<Languages> languagesList = languagesRepository.findAll();
        assertThat(languagesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Languages in Elasticsearch
        verify(mockLanguagesSearchRepository, times(0)).save(languages);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = languagesRepository.findAll().size();
        // set the field null
        languages.setCode(null);

        // Create the Languages, which fails.
        LanguagesDTO languagesDTO = languagesMapper.toDto(languages);


        restLanguagesMockMvc.perform(post("/api/languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(languagesDTO)))
            .andExpect(status().isBadRequest());

        List<Languages> languagesList = languagesRepository.findAll();
        assertThat(languagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = languagesRepository.findAll().size();
        // set the field null
        languages.setDescription(null);

        // Create the Languages, which fails.
        LanguagesDTO languagesDTO = languagesMapper.toDto(languages);


        restLanguagesMockMvc.perform(post("/api/languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(languagesDTO)))
            .andExpect(status().isBadRequest());

        List<Languages> languagesList = languagesRepository.findAll();
        assertThat(languagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllLanguages() throws Exception {
        // Initialize the database
        languagesRepository.save(languages);

        // Get all the languagesList
        restLanguagesMockMvc.perform(get("/api/languages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(languages.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    public void getLanguages() throws Exception {
        // Initialize the database
        languagesRepository.save(languages);

        // Get the languages
        restLanguagesMockMvc.perform(get("/api/languages/{id}", languages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(languages.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    public void getNonExistingLanguages() throws Exception {
        // Get the languages
        restLanguagesMockMvc.perform(get("/api/languages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateLanguages() throws Exception {
        // Initialize the database
        languagesRepository.save(languages);

        int databaseSizeBeforeUpdate = languagesRepository.findAll().size();

        // Update the languages
        Languages updatedLanguages = languagesRepository.findById(languages.getId()).get();
        updatedLanguages
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        LanguagesDTO languagesDTO = languagesMapper.toDto(updatedLanguages);

        restLanguagesMockMvc.perform(put("/api/languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(languagesDTO)))
            .andExpect(status().isOk());

        // Validate the Languages in the database
        List<Languages> languagesList = languagesRepository.findAll();
        assertThat(languagesList).hasSize(databaseSizeBeforeUpdate);
        Languages testLanguages = languagesList.get(languagesList.size() - 1);
        assertThat(testLanguages.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLanguages.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Languages in Elasticsearch
        verify(mockLanguagesSearchRepository, times(1)).save(testLanguages);
    }

    @Test
    public void updateNonExistingLanguages() throws Exception {
        int databaseSizeBeforeUpdate = languagesRepository.findAll().size();

        // Create the Languages
        LanguagesDTO languagesDTO = languagesMapper.toDto(languages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLanguagesMockMvc.perform(put("/api/languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(languagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Languages in the database
        List<Languages> languagesList = languagesRepository.findAll();
        assertThat(languagesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Languages in Elasticsearch
        verify(mockLanguagesSearchRepository, times(0)).save(languages);
    }

    @Test
    public void deleteLanguages() throws Exception {
        // Initialize the database
        languagesRepository.save(languages);

        int databaseSizeBeforeDelete = languagesRepository.findAll().size();

        // Delete the languages
        restLanguagesMockMvc.perform(delete("/api/languages/{id}", languages.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Languages> languagesList = languagesRepository.findAll();
        assertThat(languagesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Languages in Elasticsearch
        verify(mockLanguagesSearchRepository, times(1)).deleteById(languages.getId());
    }

    @Test
    public void searchLanguages() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        languagesRepository.save(languages);
        when(mockLanguagesSearchRepository.search(queryStringQuery("id:" + languages.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(languages), PageRequest.of(0, 1), 1));

        // Search the languages
        restLanguagesMockMvc.perform(get("/api/_search/languages?query=id:" + languages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(languages.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
