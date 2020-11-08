package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.RFITimeLineService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.RFITimeLineDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.RFITimeLine}.
 */
@RestController
@RequestMapping("/api")
public class RFITimeLineResource {

    private final Logger log = LoggerFactory.getLogger(RFITimeLineResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceRfiTimeLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFITimeLineService rFITimeLineService;

    public RFITimeLineResource(RFITimeLineService rFITimeLineService) {
        this.rFITimeLineService = rFITimeLineService;
    }

    /**
     * {@code POST  /rfi-time-lines} : Create a new rFITimeLine.
     *
     * @param rFITimeLineDTO the rFITimeLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFITimeLineDTO, or with status {@code 400 (Bad Request)} if the rFITimeLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfi-time-lines")
    public ResponseEntity<RFITimeLineDTO> createRFITimeLine(@Valid @RequestBody RFITimeLineDTO rFITimeLineDTO) throws URISyntaxException {
        log.debug("REST request to save RFITimeLine : {}", rFITimeLineDTO);
        if (rFITimeLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new rFITimeLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFITimeLineDTO result = rFITimeLineService.save(rFITimeLineDTO);
        return ResponseEntity.created(new URI("/api/rfi-time-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /rfi-time-lines} : Updates an existing rFITimeLine.
     *
     * @param rFITimeLineDTO the rFITimeLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFITimeLineDTO,
     * or with status {@code 400 (Bad Request)} if the rFITimeLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFITimeLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfi-time-lines")
    public ResponseEntity<RFITimeLineDTO> updateRFITimeLine(@Valid @RequestBody RFITimeLineDTO rFITimeLineDTO) throws URISyntaxException {
        log.debug("REST request to update RFITimeLine : {}", rFITimeLineDTO);
        if (rFITimeLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RFITimeLineDTO result = rFITimeLineService.save(rFITimeLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rFITimeLineDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /rfi-time-lines} : get all the rFITimeLines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFITimeLines in body.
     */
    @GetMapping("/rfi-time-lines")
    public ResponseEntity<List<RFITimeLineDTO>> getAllRFITimeLines(Pageable pageable) {
        log.debug("REST request to get a page of RFITimeLines");
        Page<RFITimeLineDTO> page = rFITimeLineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rfi-time-lines/:id} : get the "id" rFITimeLine.
     *
     * @param id the id of the rFITimeLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFITimeLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfi-time-lines/{id}")
    public ResponseEntity<RFITimeLineDTO> getRFITimeLine(@PathVariable String id) {
        log.debug("REST request to get RFITimeLine : {}", id);
        Optional<RFITimeLineDTO> rFITimeLineDTO = rFITimeLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rFITimeLineDTO);
    }

    /**
     * {@code DELETE  /rfi-time-lines/:id} : delete the "id" rFITimeLine.
     *
     * @param id the id of the rFITimeLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfi-time-lines/{id}")
    public ResponseEntity<Void> deleteRFITimeLine(@PathVariable String id) {
        log.debug("REST request to delete RFITimeLine : {}", id);
        rFITimeLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/rfi-time-lines?query=:query} : search for the rFITimeLine corresponding
     * to the query.
     *
     * @param query the query of the rFITimeLine search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/rfi-time-lines")
    public ResponseEntity<List<RFITimeLineDTO>> searchRFITimeLines(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RFITimeLines for query {}", query);
        Page<RFITimeLineDTO> page = rFITimeLineService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
