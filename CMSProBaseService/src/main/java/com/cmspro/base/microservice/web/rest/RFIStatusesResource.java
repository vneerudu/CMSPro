package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.RFIStatusesService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.RFIStatusesDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.RFIStatuses}.
 */
@RestController
@RequestMapping("/api")
public class RFIStatusesResource {

    private final Logger log = LoggerFactory.getLogger(RFIStatusesResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceRfiStatuses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFIStatusesService rFIStatusesService;

    public RFIStatusesResource(RFIStatusesService rFIStatusesService) {
        this.rFIStatusesService = rFIStatusesService;
    }

    /**
     * {@code POST  /rfi-statuses} : Create a new rFIStatuses.
     *
     * @param rFIStatusesDTO the rFIStatusesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFIStatusesDTO, or with status {@code 400 (Bad Request)} if the rFIStatuses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfi-statuses")
    public ResponseEntity<RFIStatusesDTO> createRFIStatuses(@Valid @RequestBody RFIStatusesDTO rFIStatusesDTO) throws URISyntaxException {
        log.debug("REST request to save RFIStatuses : {}", rFIStatusesDTO);
        if (rFIStatusesDTO.getId() != null) {
            throw new BadRequestAlertException("A new rFIStatuses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFIStatusesDTO result = rFIStatusesService.save(rFIStatusesDTO);
        return ResponseEntity.created(new URI("/api/rfi-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /rfi-statuses} : Updates an existing rFIStatuses.
     *
     * @param rFIStatusesDTO the rFIStatusesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFIStatusesDTO,
     * or with status {@code 400 (Bad Request)} if the rFIStatusesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFIStatusesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfi-statuses")
    public ResponseEntity<RFIStatusesDTO> updateRFIStatuses(@Valid @RequestBody RFIStatusesDTO rFIStatusesDTO) throws URISyntaxException {
        log.debug("REST request to update RFIStatuses : {}", rFIStatusesDTO);
        if (rFIStatusesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RFIStatusesDTO result = rFIStatusesService.save(rFIStatusesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rFIStatusesDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /rfi-statuses} : get all the rFIStatuses.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFIStatuses in body.
     */
    @GetMapping("/rfi-statuses")
    public ResponseEntity<List<RFIStatusesDTO>> getAllRFIStatuses(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("rfi-is-null".equals(filter)) {
            log.debug("REST request to get all RFIStatusess where rfi is null");
            return new ResponseEntity<>(rFIStatusesService.findAllWhereRfiIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of RFIStatuses");
        Page<RFIStatusesDTO> page = rFIStatusesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rfi-statuses/:id} : get the "id" rFIStatuses.
     *
     * @param id the id of the rFIStatusesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFIStatusesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfi-statuses/{id}")
    public ResponseEntity<RFIStatusesDTO> getRFIStatuses(@PathVariable String id) {
        log.debug("REST request to get RFIStatuses : {}", id);
        Optional<RFIStatusesDTO> rFIStatusesDTO = rFIStatusesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rFIStatusesDTO);
    }

    /**
     * {@code DELETE  /rfi-statuses/:id} : delete the "id" rFIStatuses.
     *
     * @param id the id of the rFIStatusesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfi-statuses/{id}")
    public ResponseEntity<Void> deleteRFIStatuses(@PathVariable String id) {
        log.debug("REST request to delete RFIStatuses : {}", id);
        rFIStatusesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/rfi-statuses?query=:query} : search for the rFIStatuses corresponding
     * to the query.
     *
     * @param query the query of the rFIStatuses search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/rfi-statuses")
    public ResponseEntity<List<RFIStatusesDTO>> searchRFIStatuses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RFIStatuses for query {}", query);
        Page<RFIStatusesDTO> page = rFIStatusesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
