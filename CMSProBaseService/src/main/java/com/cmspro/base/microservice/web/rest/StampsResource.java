package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.StampsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.StampsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.Stamps}.
 */
@RestController
@RequestMapping("/api")
public class StampsResource {

    private final Logger log = LoggerFactory.getLogger(StampsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceStamps";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StampsService stampsService;

    public StampsResource(StampsService stampsService) {
        this.stampsService = stampsService;
    }

    /**
     * {@code POST  /stamps} : Create a new stamps.
     *
     * @param stampsDTO the stampsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stampsDTO, or with status {@code 400 (Bad Request)} if the stamps has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stamps")
    public ResponseEntity<StampsDTO> createStamps(@Valid @RequestBody StampsDTO stampsDTO) throws URISyntaxException {
        log.debug("REST request to save Stamps : {}", stampsDTO);
        if (stampsDTO.getId() != null) {
            throw new BadRequestAlertException("A new stamps cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StampsDTO result = stampsService.save(stampsDTO);
        return ResponseEntity.created(new URI("/api/stamps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /stamps} : Updates an existing stamps.
     *
     * @param stampsDTO the stampsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stampsDTO,
     * or with status {@code 400 (Bad Request)} if the stampsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stampsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stamps")
    public ResponseEntity<StampsDTO> updateStamps(@Valid @RequestBody StampsDTO stampsDTO) throws URISyntaxException {
        log.debug("REST request to update Stamps : {}", stampsDTO);
        if (stampsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StampsDTO result = stampsService.save(stampsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stampsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /stamps} : get all the stamps.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stamps in body.
     */
    @GetMapping("/stamps")
    public ResponseEntity<List<StampsDTO>> getAllStamps(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("task-is-null".equals(filter)) {
            log.debug("REST request to get all Stampss where task is null");
            return new ResponseEntity<>(stampsService.findAllWhereTaskIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Stamps");
        Page<StampsDTO> page = stampsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stamps/:id} : get the "id" stamps.
     *
     * @param id the id of the stampsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stampsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stamps/{id}")
    public ResponseEntity<StampsDTO> getStamps(@PathVariable String id) {
        log.debug("REST request to get Stamps : {}", id);
        Optional<StampsDTO> stampsDTO = stampsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stampsDTO);
    }

    /**
     * {@code DELETE  /stamps/:id} : delete the "id" stamps.
     *
     * @param id the id of the stampsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stamps/{id}")
    public ResponseEntity<Void> deleteStamps(@PathVariable String id) {
        log.debug("REST request to delete Stamps : {}", id);
        stampsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/stamps?query=:query} : search for the stamps corresponding
     * to the query.
     *
     * @param query the query of the stamps search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/stamps")
    public ResponseEntity<List<StampsDTO>> searchStamps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Stamps for query {}", query);
        Page<StampsDTO> page = stampsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
