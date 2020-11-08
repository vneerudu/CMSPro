package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.SheetsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.SheetsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.Sheets}.
 */
@RestController
@RequestMapping("/api")
public class SheetsResource {

    private final Logger log = LoggerFactory.getLogger(SheetsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceSheets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SheetsService sheetsService;

    public SheetsResource(SheetsService sheetsService) {
        this.sheetsService = sheetsService;
    }

    /**
     * {@code POST  /sheets} : Create a new sheets.
     *
     * @param sheetsDTO the sheetsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sheetsDTO, or with status {@code 400 (Bad Request)} if the sheets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sheets")
    public ResponseEntity<SheetsDTO> createSheets(@Valid @RequestBody SheetsDTO sheetsDTO) throws URISyntaxException {
        log.debug("REST request to save Sheets : {}", sheetsDTO);
        if (sheetsDTO.getId() != null) {
            throw new BadRequestAlertException("A new sheets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SheetsDTO result = sheetsService.save(sheetsDTO);
        return ResponseEntity.created(new URI("/api/sheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /sheets} : Updates an existing sheets.
     *
     * @param sheetsDTO the sheetsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sheetsDTO,
     * or with status {@code 400 (Bad Request)} if the sheetsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sheetsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sheets")
    public ResponseEntity<SheetsDTO> updateSheets(@Valid @RequestBody SheetsDTO sheetsDTO) throws URISyntaxException {
        log.debug("REST request to update Sheets : {}", sheetsDTO);
        if (sheetsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SheetsDTO result = sheetsService.save(sheetsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sheetsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /sheets} : get all the sheets.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sheets in body.
     */
    @GetMapping("/sheets")
    public ResponseEntity<List<SheetsDTO>> getAllSheets(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("task-is-null".equals(filter)) {
            log.debug("REST request to get all Sheetss where task is null");
            return new ResponseEntity<>(sheetsService.findAllWhereTaskIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Sheets");
        Page<SheetsDTO> page = sheetsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sheets/:id} : get the "id" sheets.
     *
     * @param id the id of the sheetsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sheetsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sheets/{id}")
    public ResponseEntity<SheetsDTO> getSheets(@PathVariable String id) {
        log.debug("REST request to get Sheets : {}", id);
        Optional<SheetsDTO> sheetsDTO = sheetsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sheetsDTO);
    }

    /**
     * {@code DELETE  /sheets/:id} : delete the "id" sheets.
     *
     * @param id the id of the sheetsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sheets/{id}")
    public ResponseEntity<Void> deleteSheets(@PathVariable String id) {
        log.debug("REST request to delete Sheets : {}", id);
        sheetsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/sheets?query=:query} : search for the sheets corresponding
     * to the query.
     *
     * @param query the query of the sheets search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/sheets")
    public ResponseEntity<List<SheetsDTO>> searchSheets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Sheets for query {}", query);
        Page<SheetsDTO> page = sheetsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
