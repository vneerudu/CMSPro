package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.MenuItemsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.MenuItemsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.cmspro.base.microservice.domain.MenuItems}.
 */
@RestController
@RequestMapping("/api")
public class MenuItemsResource {

    private final Logger log = LoggerFactory.getLogger(MenuItemsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceMenuItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MenuItemsService menuItemsService;

    public MenuItemsResource(MenuItemsService menuItemsService) {
        this.menuItemsService = menuItemsService;
    }

    /**
     * {@code POST  /menu-items} : Create a new menuItems.
     *
     * @param menuItemsDTO the menuItemsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new menuItemsDTO, or with status {@code 400 (Bad Request)} if the menuItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/menu-items")
    public ResponseEntity<MenuItemsDTO> createMenuItems(@Valid @RequestBody MenuItemsDTO menuItemsDTO) throws URISyntaxException {
        log.debug("REST request to save MenuItems : {}", menuItemsDTO);
        if (menuItemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new menuItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MenuItemsDTO result = menuItemsService.save(menuItemsDTO);
        return ResponseEntity.created(new URI("/api/menu-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /menu-items} : Updates an existing menuItems.
     *
     * @param menuItemsDTO the menuItemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menuItemsDTO,
     * or with status {@code 400 (Bad Request)} if the menuItemsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the menuItemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/menu-items")
    public ResponseEntity<MenuItemsDTO> updateMenuItems(@Valid @RequestBody MenuItemsDTO menuItemsDTO) throws URISyntaxException {
        log.debug("REST request to update MenuItems : {}", menuItemsDTO);
        if (menuItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MenuItemsDTO result = menuItemsService.save(menuItemsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, menuItemsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /menu-items} : get all the menuItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of menuItems in body.
     */
    @GetMapping("/menu-items")
    public ResponseEntity<List<MenuItemsDTO>> getAllMenuItems(Pageable pageable) {
        log.debug("REST request to get a page of MenuItems");
        Page<MenuItemsDTO> page = menuItemsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /menu-items/:id} : get the "id" menuItems.
     *
     * @param id the id of the menuItemsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the menuItemsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/menu-items/{id}")
    public ResponseEntity<MenuItemsDTO> getMenuItems(@PathVariable String id) {
        log.debug("REST request to get MenuItems : {}", id);
        Optional<MenuItemsDTO> menuItemsDTO = menuItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(menuItemsDTO);
    }

    /**
     * {@code DELETE  /menu-items/:id} : delete the "id" menuItems.
     *
     * @param id the id of the menuItemsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/menu-items/{id}")
    public ResponseEntity<Void> deleteMenuItems(@PathVariable String id) {
        log.debug("REST request to delete MenuItems : {}", id);
        menuItemsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/menu-items?query=:query} : search for the menuItems corresponding
     * to the query.
     *
     * @param query the query of the menuItems search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/menu-items")
    public ResponseEntity<List<MenuItemsDTO>> searchMenuItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MenuItems for query {}", query);
        Page<MenuItemsDTO> page = menuItemsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
