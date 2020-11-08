package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.SheetHistoryService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.SheetHistoryDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.SheetHistory}.
 */
@RestController
@RequestMapping("/api")
public class SheetHistoryResource {

    private final Logger log = LoggerFactory.getLogger(SheetHistoryResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceSheetHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SheetHistoryService sheetHistoryService;

    public SheetHistoryResource(SheetHistoryService sheetHistoryService) {
        this.sheetHistoryService = sheetHistoryService;
    }

    /**
     * {@code POST  /sheet-histories} : Create a new sheetHistory.
     *
     * @param sheetHistoryDTO the sheetHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sheetHistoryDTO, or with status {@code 400 (Bad Request)} if the sheetHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sheet-histories")
    public ResponseEntity<SheetHistoryDTO> createSheetHistory(@Valid @RequestBody SheetHistoryDTO sheetHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save SheetHistory : {}", sheetHistoryDTO);
        if (sheetHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new sheetHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SheetHistoryDTO result = sheetHistoryService.save(sheetHistoryDTO);
        return ResponseEntity.created(new URI("/api/sheet-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /sheet-histories} : Updates an existing sheetHistory.
     *
     * @param sheetHistoryDTO the sheetHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sheetHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the sheetHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sheetHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sheet-histories")
    public ResponseEntity<SheetHistoryDTO> updateSheetHistory(@Valid @RequestBody SheetHistoryDTO sheetHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update SheetHistory : {}", sheetHistoryDTO);
        if (sheetHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SheetHistoryDTO result = sheetHistoryService.save(sheetHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sheetHistoryDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /sheet-histories} : get all the sheetHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sheetHistories in body.
     */
    @GetMapping("/sheet-histories")
    public ResponseEntity<List<SheetHistoryDTO>> getAllSheetHistories(Pageable pageable) {
        log.debug("REST request to get a page of SheetHistories");
        Page<SheetHistoryDTO> page = sheetHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sheet-histories/:id} : get the "id" sheetHistory.
     *
     * @param id the id of the sheetHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sheetHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sheet-histories/{id}")
    public ResponseEntity<SheetHistoryDTO> getSheetHistory(@PathVariable String id) {
        log.debug("REST request to get SheetHistory : {}", id);
        Optional<SheetHistoryDTO> sheetHistoryDTO = sheetHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sheetHistoryDTO);
    }

    /**
     * {@code DELETE  /sheet-histories/:id} : delete the "id" sheetHistory.
     *
     * @param id the id of the sheetHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sheet-histories/{id}")
    public ResponseEntity<Void> deleteSheetHistory(@PathVariable String id) {
        log.debug("REST request to delete SheetHistory : {}", id);
        sheetHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/sheet-histories?query=:query} : search for the sheetHistory corresponding
     * to the query.
     *
     * @param query the query of the sheetHistory search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/sheet-histories")
    public ResponseEntity<List<SheetHistoryDTO>> searchSheetHistories(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SheetHistories for query {}", query);
        Page<SheetHistoryDTO> page = sheetHistoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
