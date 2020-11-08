package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.AddressTypes;
import com.cmspro.base.microservice.repository.AddressTypesRepository;
import com.cmspro.base.microservice.repository.search.AddressTypesSearchRepository;
import com.cmspro.base.microservice.service.AddressTypesService;
import com.cmspro.base.microservice.service.dto.AddressTypesDTO;
import com.cmspro.base.microservice.service.mapper.AddressTypesMapper;

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
 * Integration tests for the {@link AddressTypesResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AddressTypesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AddressTypesRepository addressTypesRepository;

    @Autowired
    private AddressTypesMapper addressTypesMapper;

    @Autowired
    private AddressTypesService addressTypesService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.AddressTypesSearchRepositoryMockConfiguration
     */
    @Autowired
    private AddressTypesSearchRepository mockAddressTypesSearchRepository;

    @Autowired
    private MockMvc restAddressTypesMockMvc;

    private AddressTypes addressTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressTypes createEntity() {
        AddressTypes addressTypes = new AddressTypes()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return addressTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressTypes createUpdatedEntity() {
        AddressTypes addressTypes = new AddressTypes()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return addressTypes;
    }

    @BeforeEach
    public void initTest() {
        addressTypesRepository.deleteAll();
        addressTypes = createEntity();
    }

    @Test
    public void createAddressTypes() throws Exception {
        int databaseSizeBeforeCreate = addressTypesRepository.findAll().size();
        // Create the AddressTypes
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);
        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeCreate + 1);
        AddressTypes testAddressTypes = addressTypesList.get(addressTypesList.size() - 1);
        assertThat(testAddressTypes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAddressTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the AddressTypes in Elasticsearch
        verify(mockAddressTypesSearchRepository, times(1)).save(testAddressTypes);
    }

    @Test
    public void createAddressTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressTypesRepository.findAll().size();

        // Create the AddressTypes with an existing ID
        addressTypes.setId("existing_id");
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeCreate);

        // Validate the AddressTypes in Elasticsearch
        verify(mockAddressTypesSearchRepository, times(0)).save(addressTypes);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressTypesRepository.findAll().size();
        // set the field null
        addressTypes.setCode(null);

        // Create the AddressTypes, which fails.
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);


        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressTypesRepository.findAll().size();
        // set the field null
        addressTypes.setDescription(null);

        // Create the AddressTypes, which fails.
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);


        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.save(addressTypes);

        // Get all the addressTypesList
        restAddressTypesMockMvc.perform(get("/api/address-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressTypes.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    public void getAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.save(addressTypes);

        // Get the addressTypes
        restAddressTypesMockMvc.perform(get("/api/address-types/{id}", addressTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(addressTypes.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    public void getNonExistingAddressTypes() throws Exception {
        // Get the addressTypes
        restAddressTypesMockMvc.perform(get("/api/address-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.save(addressTypes);

        int databaseSizeBeforeUpdate = addressTypesRepository.findAll().size();

        // Update the addressTypes
        AddressTypes updatedAddressTypes = addressTypesRepository.findById(addressTypes.getId()).get();
        updatedAddressTypes
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(updatedAddressTypes);

        restAddressTypesMockMvc.perform(put("/api/address-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isOk());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeUpdate);
        AddressTypes testAddressTypes = addressTypesList.get(addressTypesList.size() - 1);
        assertThat(testAddressTypes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAddressTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the AddressTypes in Elasticsearch
        verify(mockAddressTypesSearchRepository, times(1)).save(testAddressTypes);
    }

    @Test
    public void updateNonExistingAddressTypes() throws Exception {
        int databaseSizeBeforeUpdate = addressTypesRepository.findAll().size();

        // Create the AddressTypes
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressTypesMockMvc.perform(put("/api/address-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AddressTypes in Elasticsearch
        verify(mockAddressTypesSearchRepository, times(0)).save(addressTypes);
    }

    @Test
    public void deleteAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.save(addressTypes);

        int databaseSizeBeforeDelete = addressTypesRepository.findAll().size();

        // Delete the addressTypes
        restAddressTypesMockMvc.perform(delete("/api/address-types/{id}", addressTypes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AddressTypes in Elasticsearch
        verify(mockAddressTypesSearchRepository, times(1)).deleteById(addressTypes.getId());
    }

    @Test
    public void searchAddressTypes() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        addressTypesRepository.save(addressTypes);
        when(mockAddressTypesSearchRepository.search(queryStringQuery("id:" + addressTypes.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(addressTypes), PageRequest.of(0, 1), 1));

        // Search the addressTypes
        restAddressTypesMockMvc.perform(get("/api/_search/address-types?query=id:" + addressTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressTypes.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
