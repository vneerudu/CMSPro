package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.CmsProBaseServiceApp;
import com.cmspro.base.microservice.domain.MenuItems;
import com.cmspro.base.microservice.repository.MenuItemsRepository;
import com.cmspro.base.microservice.repository.search.MenuItemsSearchRepository;
import com.cmspro.base.microservice.service.MenuItemsService;
import com.cmspro.base.microservice.service.dto.MenuItemsDTO;
import com.cmspro.base.microservice.service.mapper.MenuItemsMapper;

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
 * Integration tests for the {@link MenuItemsResource} REST controller.
 */
@SpringBootTest(classes = CmsProBaseServiceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MenuItemsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private MenuItemsRepository menuItemsRepository;

    @Autowired
    private MenuItemsMapper menuItemsMapper;

    @Autowired
    private MenuItemsService menuItemsService;

    /**
     * This repository is mocked in the com.cmspro.base.microservice.repository.search test package.
     *
     * @see com.cmspro.base.microservice.repository.search.MenuItemsSearchRepositoryMockConfiguration
     */
    @Autowired
    private MenuItemsSearchRepository mockMenuItemsSearchRepository;

    @Autowired
    private MockMvc restMenuItemsMockMvc;

    private MenuItems menuItems;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuItems createEntity() {
        MenuItems menuItems = new MenuItems()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE);
        return menuItems;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuItems createUpdatedEntity() {
        MenuItems menuItems = new MenuItems()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        return menuItems;
    }

    @BeforeEach
    public void initTest() {
        menuItemsRepository.deleteAll();
        menuItems = createEntity();
    }

    @Test
    public void createMenuItems() throws Exception {
        int databaseSizeBeforeCreate = menuItemsRepository.findAll().size();
        // Create the MenuItems
        MenuItemsDTO menuItemsDTO = menuItemsMapper.toDto(menuItems);
        restMenuItemsMockMvc.perform(post("/api/menu-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menuItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the MenuItems in the database
        List<MenuItems> menuItemsList = menuItemsRepository.findAll();
        assertThat(menuItemsList).hasSize(databaseSizeBeforeCreate + 1);
        MenuItems testMenuItems = menuItemsList.get(menuItemsList.size() - 1);
        assertThat(testMenuItems.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMenuItems.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMenuItems.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the MenuItems in Elasticsearch
        verify(mockMenuItemsSearchRepository, times(1)).save(testMenuItems);
    }

    @Test
    public void createMenuItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menuItemsRepository.findAll().size();

        // Create the MenuItems with an existing ID
        menuItems.setId("existing_id");
        MenuItemsDTO menuItemsDTO = menuItemsMapper.toDto(menuItems);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuItemsMockMvc.perform(post("/api/menu-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menuItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MenuItems in the database
        List<MenuItems> menuItemsList = menuItemsRepository.findAll();
        assertThat(menuItemsList).hasSize(databaseSizeBeforeCreate);

        // Validate the MenuItems in Elasticsearch
        verify(mockMenuItemsSearchRepository, times(0)).save(menuItems);
    }


    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuItemsRepository.findAll().size();
        // set the field null
        menuItems.setCode(null);

        // Create the MenuItems, which fails.
        MenuItemsDTO menuItemsDTO = menuItemsMapper.toDto(menuItems);


        restMenuItemsMockMvc.perform(post("/api/menu-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menuItemsDTO)))
            .andExpect(status().isBadRequest());

        List<MenuItems> menuItemsList = menuItemsRepository.findAll();
        assertThat(menuItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuItemsRepository.findAll().size();
        // set the field null
        menuItems.setDescription(null);

        // Create the MenuItems, which fails.
        MenuItemsDTO menuItemsDTO = menuItemsMapper.toDto(menuItems);


        restMenuItemsMockMvc.perform(post("/api/menu-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menuItemsDTO)))
            .andExpect(status().isBadRequest());

        List<MenuItems> menuItemsList = menuItemsRepository.findAll();
        assertThat(menuItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllMenuItems() throws Exception {
        // Initialize the database
        menuItemsRepository.save(menuItems);

        // Get all the menuItemsList
        restMenuItemsMockMvc.perform(get("/api/menu-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuItems.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    public void getMenuItems() throws Exception {
        // Initialize the database
        menuItemsRepository.save(menuItems);

        // Get the menuItems
        restMenuItemsMockMvc.perform(get("/api/menu-items/{id}", menuItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(menuItems.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    public void getNonExistingMenuItems() throws Exception {
        // Get the menuItems
        restMenuItemsMockMvc.perform(get("/api/menu-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMenuItems() throws Exception {
        // Initialize the database
        menuItemsRepository.save(menuItems);

        int databaseSizeBeforeUpdate = menuItemsRepository.findAll().size();

        // Update the menuItems
        MenuItems updatedMenuItems = menuItemsRepository.findById(menuItems.getId()).get();
        updatedMenuItems
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE);
        MenuItemsDTO menuItemsDTO = menuItemsMapper.toDto(updatedMenuItems);

        restMenuItemsMockMvc.perform(put("/api/menu-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menuItemsDTO)))
            .andExpect(status().isOk());

        // Validate the MenuItems in the database
        List<MenuItems> menuItemsList = menuItemsRepository.findAll();
        assertThat(menuItemsList).hasSize(databaseSizeBeforeUpdate);
        MenuItems testMenuItems = menuItemsList.get(menuItemsList.size() - 1);
        assertThat(testMenuItems.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMenuItems.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMenuItems.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the MenuItems in Elasticsearch
        verify(mockMenuItemsSearchRepository, times(1)).save(testMenuItems);
    }

    @Test
    public void updateNonExistingMenuItems() throws Exception {
        int databaseSizeBeforeUpdate = menuItemsRepository.findAll().size();

        // Create the MenuItems
        MenuItemsDTO menuItemsDTO = menuItemsMapper.toDto(menuItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuItemsMockMvc.perform(put("/api/menu-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(menuItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MenuItems in the database
        List<MenuItems> menuItemsList = menuItemsRepository.findAll();
        assertThat(menuItemsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MenuItems in Elasticsearch
        verify(mockMenuItemsSearchRepository, times(0)).save(menuItems);
    }

    @Test
    public void deleteMenuItems() throws Exception {
        // Initialize the database
        menuItemsRepository.save(menuItems);

        int databaseSizeBeforeDelete = menuItemsRepository.findAll().size();

        // Delete the menuItems
        restMenuItemsMockMvc.perform(delete("/api/menu-items/{id}", menuItems.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MenuItems> menuItemsList = menuItemsRepository.findAll();
        assertThat(menuItemsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MenuItems in Elasticsearch
        verify(mockMenuItemsSearchRepository, times(1)).deleteById(menuItems.getId());
    }

    @Test
    public void searchMenuItems() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        menuItemsRepository.save(menuItems);
        when(mockMenuItemsSearchRepository.search(queryStringQuery("id:" + menuItems.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(menuItems), PageRequest.of(0, 1), 1));

        // Search the menuItems
        restMenuItemsMockMvc.perform(get("/api/_search/menu-items?query=id:" + menuItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuItems.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
}
