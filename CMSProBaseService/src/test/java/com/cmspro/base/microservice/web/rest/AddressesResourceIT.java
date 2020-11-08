package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.Addresses;
import com.cmspro.base.microservice.repository.AddressesRepository;
import com.cmspro.base.microservice.repository.search.AddressesSearchRepository;
import com.cmspro.base.microservice.service.AddressesService;
import com.cmspro.base.microservice.service.dto.AddressesDTO;
import com.cmspro.base.microservice.service.mapper.AddressesMapper;

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
 * Integration tests for the {@link AddressesResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AddressesResourceIT {

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final Long DEFAULT_ZIP_CODE = 1L;
    private static final Long UPDATED_ZIP_CODE = 2L;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AddressesRepository addressesRepository;

    @Autowired
    private AddressesMapper addressesMapper;

    @Autowired
    private AddressesService addressesService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.AddressesSearchRepositoryMockConfiguration
     */
    @Autowired
    private AddressesSearchRepository mockAddressesSearchRepository;

    @Autowired
    private MockMvc restAddressesMockMvc;

    private Addresses addresses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Addresses createEntity() {
        Addresses addresses = new Addresses()
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .city(DEFAULT_CITY)
            .zipCode(DEFAULT_ZIP_CODE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        return addresses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Addresses createUpdatedEntity() {
        Addresses addresses = new Addresses()
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .zipCode(UPDATED_ZIP_CODE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        return addresses;
    }

    @BeforeEach
    public void initTest() {
        addressesRepository.deleteAll();
        addresses = createEntity();
    }

    @Test
    public void createAddresses() throws Exception {
        int databaseSizeBeforeCreate = addressesRepository.findAll().size();
        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);
        restAddressesMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isCreated());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeCreate + 1);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testAddresses.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testAddresses.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddresses.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testAddresses.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testAddresses.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAddresses.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the Addresses in Elasticsearch
        verify(mockAddressesSearchRepository, times(1)).save(testAddresses);
    }

    @Test
    public void createAddressesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressesRepository.findAll().size();

        // Create the Addresses with an existing ID
        addresses.setId("existing_id");
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressesMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Addresses in Elasticsearch
        verify(mockAddressesSearchRepository, times(0)).save(addresses);
    }


    @Test
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressesRepository.findAll().size();
        // set the field null
        addresses.setCreatedBy(null);

        // Create the Addresses, which fails.
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);


        restAddressesMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressesRepository.findAll().size();
        // set the field null
        addresses.setCreationDate(null);

        // Create the Addresses, which fails.
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);


        restAddressesMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressesRepository.save(addresses);

        // Get all the addressesList
        restAddressesMockMvc.perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    public void getAddresses() throws Exception {
        // Initialize the database
        addressesRepository.save(addresses);

        // Get the addresses
        restAddressesMockMvc.perform(get("/api/addresses/{id}", addresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(addresses.getId()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    public void getNonExistingAddresses() throws Exception {
        // Get the addresses
        restAddressesMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAddresses() throws Exception {
        // Initialize the database
        addressesRepository.save(addresses);

        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses
        Addresses updatedAddresses = addressesRepository.findById(addresses.getId()).get();
        updatedAddresses
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .zipCode(UPDATED_ZIP_CODE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        AddressesDTO addressesDTO = addressesMapper.toDto(updatedAddresses);

        restAddressesMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testAddresses.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testAddresses.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddresses.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testAddresses.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAddresses.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAddresses.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);

        // Validate the Addresses in Elasticsearch
        verify(mockAddressesSearchRepository, times(1)).save(testAddresses);
    }

    @Test
    public void updateNonExistingAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressesMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Addresses in Elasticsearch
        verify(mockAddressesSearchRepository, times(0)).save(addresses);
    }

    @Test
    public void deleteAddresses() throws Exception {
        // Initialize the database
        addressesRepository.save(addresses);

        int databaseSizeBeforeDelete = addressesRepository.findAll().size();

        // Delete the addresses
        restAddressesMockMvc.perform(delete("/api/addresses/{id}", addresses.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Addresses in Elasticsearch
        verify(mockAddressesSearchRepository, times(1)).deleteById(addresses.getId());
    }

    @Test
    public void searchAddresses() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        addressesRepository.save(addresses);
        when(mockAddressesSearchRepository.search(queryStringQuery("id:" + addresses.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(addresses), PageRequest.of(0, 1), 1));

        // Search the addresses
        restAddressesMockMvc.perform(get("/api/_search/addresses?query=id:" + addresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
}
