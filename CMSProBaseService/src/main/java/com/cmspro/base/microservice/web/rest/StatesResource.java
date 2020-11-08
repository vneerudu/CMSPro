package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.StatesService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.StatesDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.States}.
 */
@RestController
@RequestMapping("/api")
public class StatesResource {

    private final Logger log = LoggerFactory.getLogger(StatesResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceStates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatesService statesService;

    public StatesResource(StatesService statesService) {
        this.statesService = statesService;
    }

    /**
     * {@code POST  /states} : Create a new states.
     *
     * @param statesDTO the statesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statesDTO, or with status {@code 400 (Bad Request)} if the states has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/states")
    public ResponseEntity<StatesDTO> createStates(@Valid @RequestBody StatesDTO statesDTO) throws URISyntaxException {
        log.debug("REST request to save States : {}", statesDTO);
        if (statesDTO.getId() != null) {
            throw new BadRequestAlertException("A new states cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatesDTO result = statesService.save(statesDTO);
        return ResponseEntity.created(new URI("/api/states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /states} : Updates an existing states.
     *
     * @param statesDTO the statesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statesDTO,
     * or with status {@code 400 (Bad Request)} if the statesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/states")
    public ResponseEntity<StatesDTO> updateStates(@Valid @RequestBody StatesDTO statesDTO) throws URISyntaxException {
        log.debug("REST request to update States : {}", statesDTO);
        if (statesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatesDTO result = statesService.save(statesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, statesDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /states} : get all the states.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of states in body.
     */
    @GetMapping("/states")
    public ResponseEntity<List<StatesDTO>> getAllStates(Pageable pageable) {
        log.debug("REST request to get a page of States");
        Page<StatesDTO> page = statesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /states/:id} : get the "id" states.
     *
     * @param id the id of the statesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/states/{id}")
    public ResponseEntity<StatesDTO> getStates(@PathVariable String id) {
        log.debug("REST request to get States : {}", id);
        Optional<StatesDTO> statesDTO = statesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statesDTO);
    }

    /**
     * {@code DELETE  /states/:id} : delete the "id" states.
     *
     * @param id the id of the statesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/states/{id}")
    public ResponseEntity<Void> deleteStates(@PathVariable String id) {
        log.debug("REST request to delete States : {}", id);
        statesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/states?query=:query} : search for the states corresponding
     * to the query.
     *
     * @param query the query of the states search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/states")
    public ResponseEntity<List<StatesDTO>> searchStates(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of States for query {}", query);
        Page<StatesDTO> page = statesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
