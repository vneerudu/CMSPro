package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.RFICommentsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.RFICommentsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.RFIComments}.
 */
@RestController
@RequestMapping("/api")
public class RFICommentsResource {

    private final Logger log = LoggerFactory.getLogger(RFICommentsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceRfiComments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFICommentsService rFICommentsService;

    public RFICommentsResource(RFICommentsService rFICommentsService) {
        this.rFICommentsService = rFICommentsService;
    }

    /**
     * {@code POST  /rfi-comments} : Create a new rFIComments.
     *
     * @param rFICommentsDTO the rFICommentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFICommentsDTO, or with status {@code 400 (Bad Request)} if the rFIComments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfi-comments")
    public ResponseEntity<RFICommentsDTO> createRFIComments(@Valid @RequestBody RFICommentsDTO rFICommentsDTO) throws URISyntaxException {
        log.debug("REST request to save RFIComments : {}", rFICommentsDTO);
        if (rFICommentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new rFIComments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFICommentsDTO result = rFICommentsService.save(rFICommentsDTO);
        return ResponseEntity.created(new URI("/api/rfi-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /rfi-comments} : Updates an existing rFIComments.
     *
     * @param rFICommentsDTO the rFICommentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFICommentsDTO,
     * or with status {@code 400 (Bad Request)} if the rFICommentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFICommentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfi-comments")
    public ResponseEntity<RFICommentsDTO> updateRFIComments(@Valid @RequestBody RFICommentsDTO rFICommentsDTO) throws URISyntaxException {
        log.debug("REST request to update RFIComments : {}", rFICommentsDTO);
        if (rFICommentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RFICommentsDTO result = rFICommentsService.save(rFICommentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rFICommentsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /rfi-comments} : get all the rFIComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFIComments in body.
     */
    @GetMapping("/rfi-comments")
    public ResponseEntity<List<RFICommentsDTO>> getAllRFIComments(Pageable pageable) {
        log.debug("REST request to get a page of RFIComments");
        Page<RFICommentsDTO> page = rFICommentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rfi-comments/:id} : get the "id" rFIComments.
     *
     * @param id the id of the rFICommentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFICommentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfi-comments/{id}")
    public ResponseEntity<RFICommentsDTO> getRFIComments(@PathVariable String id) {
        log.debug("REST request to get RFIComments : {}", id);
        Optional<RFICommentsDTO> rFICommentsDTO = rFICommentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rFICommentsDTO);
    }

    /**
     * {@code DELETE  /rfi-comments/:id} : delete the "id" rFIComments.
     *
     * @param id the id of the rFICommentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfi-comments/{id}")
    public ResponseEntity<Void> deleteRFIComments(@PathVariable String id) {
        log.debug("REST request to delete RFIComments : {}", id);
        rFICommentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/rfi-comments?query=:query} : search for the rFIComments corresponding
     * to the query.
     *
     * @param query the query of the rFIComments search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/rfi-comments")
    public ResponseEntity<List<RFICommentsDTO>> searchRFIComments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RFIComments for query {}", query);
        Page<RFICommentsDTO> page = rFICommentsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
