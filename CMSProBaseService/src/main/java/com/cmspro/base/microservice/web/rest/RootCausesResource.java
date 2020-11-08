package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.RootCausesService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.RootCausesDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.RootCauses}.
 */
@RestController
@RequestMapping("/api")
public class RootCausesResource {

    private final Logger log = LoggerFactory.getLogger(RootCausesResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceRootCauses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RootCausesService rootCausesService;

    public RootCausesResource(RootCausesService rootCausesService) {
        this.rootCausesService = rootCausesService;
    }

    /**
     * {@code POST  /root-causes} : Create a new rootCauses.
     *
     * @param rootCausesDTO the rootCausesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rootCausesDTO, or with status {@code 400 (Bad Request)} if the rootCauses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/root-causes")
    public ResponseEntity<RootCausesDTO> createRootCauses(@Valid @RequestBody RootCausesDTO rootCausesDTO) throws URISyntaxException {
        log.debug("REST request to save RootCauses : {}", rootCausesDTO);
        if (rootCausesDTO.getId() != null) {
            throw new BadRequestAlertException("A new rootCauses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RootCausesDTO result = rootCausesService.save(rootCausesDTO);
        return ResponseEntity.created(new URI("/api/root-causes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /root-causes} : Updates an existing rootCauses.
     *
     * @param rootCausesDTO the rootCausesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rootCausesDTO,
     * or with status {@code 400 (Bad Request)} if the rootCausesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rootCausesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/root-causes")
    public ResponseEntity<RootCausesDTO> updateRootCauses(@Valid @RequestBody RootCausesDTO rootCausesDTO) throws URISyntaxException {
        log.debug("REST request to update RootCauses : {}", rootCausesDTO);
        if (rootCausesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RootCausesDTO result = rootCausesService.save(rootCausesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rootCausesDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /root-causes} : get all the rootCauses.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rootCauses in body.
     */
    @GetMapping("/root-causes")
    public ResponseEntity<List<RootCausesDTO>> getAllRootCauses(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("task-is-null".equals(filter)) {
            log.debug("REST request to get all RootCausess where task is null");
            return new ResponseEntity<>(rootCausesService.findAllWhereTaskIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of RootCauses");
        Page<RootCausesDTO> page = rootCausesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /root-causes/:id} : get the "id" rootCauses.
     *
     * @param id the id of the rootCausesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rootCausesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/root-causes/{id}")
    public ResponseEntity<RootCausesDTO> getRootCauses(@PathVariable String id) {
        log.debug("REST request to get RootCauses : {}", id);
        Optional<RootCausesDTO> rootCausesDTO = rootCausesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rootCausesDTO);
    }

    /**
     * {@code DELETE  /root-causes/:id} : delete the "id" rootCauses.
     *
     * @param id the id of the rootCausesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/root-causes/{id}")
    public ResponseEntity<Void> deleteRootCauses(@PathVariable String id) {
        log.debug("REST request to delete RootCauses : {}", id);
        rootCausesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/root-causes?query=:query} : search for the rootCauses corresponding
     * to the query.
     *
     * @param query the query of the rootCauses search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/root-causes")
    public ResponseEntity<List<RootCausesDTO>> searchRootCauses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RootCauses for query {}", query);
        Page<RootCausesDTO> page = rootCausesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
