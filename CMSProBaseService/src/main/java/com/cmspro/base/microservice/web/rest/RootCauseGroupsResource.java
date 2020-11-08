package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.RootCauseGroupsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.RootCauseGroupsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.RootCauseGroups}.
 */
@RestController
@RequestMapping("/api")
public class RootCauseGroupsResource {

    private final Logger log = LoggerFactory.getLogger(RootCauseGroupsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceRootCauseGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RootCauseGroupsService rootCauseGroupsService;

    public RootCauseGroupsResource(RootCauseGroupsService rootCauseGroupsService) {
        this.rootCauseGroupsService = rootCauseGroupsService;
    }

    /**
     * {@code POST  /root-cause-groups} : Create a new rootCauseGroups.
     *
     * @param rootCauseGroupsDTO the rootCauseGroupsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rootCauseGroupsDTO, or with status {@code 400 (Bad Request)} if the rootCauseGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/root-cause-groups")
    public ResponseEntity<RootCauseGroupsDTO> createRootCauseGroups(@Valid @RequestBody RootCauseGroupsDTO rootCauseGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save RootCauseGroups : {}", rootCauseGroupsDTO);
        if (rootCauseGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new rootCauseGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RootCauseGroupsDTO result = rootCauseGroupsService.save(rootCauseGroupsDTO);
        return ResponseEntity.created(new URI("/api/root-cause-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /root-cause-groups} : Updates an existing rootCauseGroups.
     *
     * @param rootCauseGroupsDTO the rootCauseGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rootCauseGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the rootCauseGroupsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rootCauseGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/root-cause-groups")
    public ResponseEntity<RootCauseGroupsDTO> updateRootCauseGroups(@Valid @RequestBody RootCauseGroupsDTO rootCauseGroupsDTO) throws URISyntaxException {
        log.debug("REST request to update RootCauseGroups : {}", rootCauseGroupsDTO);
        if (rootCauseGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RootCauseGroupsDTO result = rootCauseGroupsService.save(rootCauseGroupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rootCauseGroupsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /root-cause-groups} : get all the rootCauseGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rootCauseGroups in body.
     */
    @GetMapping("/root-cause-groups")
    public ResponseEntity<List<RootCauseGroupsDTO>> getAllRootCauseGroups(Pageable pageable) {
        log.debug("REST request to get a page of RootCauseGroups");
        Page<RootCauseGroupsDTO> page = rootCauseGroupsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /root-cause-groups/:id} : get the "id" rootCauseGroups.
     *
     * @param id the id of the rootCauseGroupsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rootCauseGroupsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/root-cause-groups/{id}")
    public ResponseEntity<RootCauseGroupsDTO> getRootCauseGroups(@PathVariable String id) {
        log.debug("REST request to get RootCauseGroups : {}", id);
        Optional<RootCauseGroupsDTO> rootCauseGroupsDTO = rootCauseGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rootCauseGroupsDTO);
    }

    /**
     * {@code DELETE  /root-cause-groups/:id} : delete the "id" rootCauseGroups.
     *
     * @param id the id of the rootCauseGroupsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/root-cause-groups/{id}")
    public ResponseEntity<Void> deleteRootCauseGroups(@PathVariable String id) {
        log.debug("REST request to delete RootCauseGroups : {}", id);
        rootCauseGroupsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/root-cause-groups?query=:query} : search for the rootCauseGroups corresponding
     * to the query.
     *
     * @param query the query of the rootCauseGroups search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/root-cause-groups")
    public ResponseEntity<List<RootCauseGroupsDTO>> searchRootCauseGroups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RootCauseGroups for query {}", query);
        Page<RootCauseGroupsDTO> page = rootCauseGroupsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
