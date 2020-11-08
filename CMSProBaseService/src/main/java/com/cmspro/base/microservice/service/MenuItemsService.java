package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.MenuItems;
import com.cmspro.base.microservice.repository.MenuItemsRepository;
import com.cmspro.base.microservice.repository.search.MenuItemsSearchRepository;
import com.cmspro.base.microservice.service.dto.MenuItemsDTO;
import com.cmspro.base.microservice.service.mapper.MenuItemsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MenuItems}.
 */
@Service
public class MenuItemsService {

    private final Logger log = LoggerFactory.getLogger(MenuItemsService.class);

    private final MenuItemsRepository menuItemsRepository;

    private final MenuItemsMapper menuItemsMapper;

    private final MenuItemsSearchRepository menuItemsSearchRepository;

    public MenuItemsService(MenuItemsRepository menuItemsRepository, MenuItemsMapper menuItemsMapper, MenuItemsSearchRepository menuItemsSearchRepository) {
        this.menuItemsRepository = menuItemsRepository;
        this.menuItemsMapper = menuItemsMapper;
        this.menuItemsSearchRepository = menuItemsSearchRepository;
    }

    /**
     * Save a menuItems.
     *
     * @param menuItemsDTO the entity to save.
     * @return the persisted entity.
     */
    public MenuItemsDTO save(MenuItemsDTO menuItemsDTO) {
        log.debug("Request to save MenuItems : {}", menuItemsDTO);
        MenuItems menuItems = menuItemsMapper.toEntity(menuItemsDTO);
        menuItems = menuItemsRepository.save(menuItems);
        MenuItemsDTO result = menuItemsMapper.toDto(menuItems);
        menuItemsSearchRepository.save(menuItems);
        return result;
    }

    /**
     * Get all the menuItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<MenuItemsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MenuItems");
        return menuItemsRepository.findAll(pageable)
            .map(menuItemsMapper::toDto);
    }


    /**
     * Get one menuItems by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<MenuItemsDTO> findOne(String id) {
        log.debug("Request to get MenuItems : {}", id);
        return menuItemsRepository.findById(id)
            .map(menuItemsMapper::toDto);
    }

    /**
     * Delete the menuItems by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete MenuItems : {}", id);
        menuItemsRepository.deleteById(id);
        menuItemsSearchRepository.deleteById(id);
    }

    /**
     * Search for the menuItems corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<MenuItemsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MenuItems for query {}", query);
        return menuItemsSearchRepository.search(queryStringQuery(query), pageable)
            .map(menuItemsMapper::toDto);
    }
}
