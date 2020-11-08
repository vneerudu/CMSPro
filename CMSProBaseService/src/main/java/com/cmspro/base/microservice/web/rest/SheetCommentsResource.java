package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.SheetCommentsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.SheetCommentsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.SheetComments}.
 */
@RestController
@RequestMapping("/api")
public class SheetCommentsResource {

    private final Logger log = LoggerFactory.getLogger(SheetCommentsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceSheetComments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SheetCommentsService sheetCommentsService;

    public SheetCommentsResource(SheetCommentsService sheetCommentsService) {
        this.sheetCommentsService = sheetCommentsService;
    }

    /**
     * {@code POST  /sheet-comments} : Create a new sheetComments.
     *
     * @param sheetCommentsDTO the sheetCommentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sheetCommentsDTO, or with status {@code 400 (Bad Request)} if the sheetComments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sheet-comments")
    public ResponseEntity<SheetCommentsDTO> createSheetComments(@Valid @RequestBody SheetCommentsDTO sheetCommentsDTO) throws URISyntaxException {
        log.debug("REST request to save SheetComments : {}", sheetCommentsDTO);
        if (sheetCommentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new sheetComments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SheetCommentsDTO result = sheetCommentsService.save(sheetCommentsDTO);
        return ResponseEntity.created(new URI("/api/sheet-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /sheet-comments} : Updates an existing sheetComments.
     *
     * @param sheetCommentsDTO the sheetCommentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sheetCommentsDTO,
     * or with status {@code 400 (Bad Request)} if the sheetCommentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sheetCommentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sheet-comments")
    public ResponseEntity<SheetCommentsDTO> updateSheetComments(@Valid @RequestBody SheetCommentsDTO sheetCommentsDTO) throws URISyntaxException {
        log.debug("REST request to update SheetComments : {}", sheetCommentsDTO);
        if (sheetCommentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SheetCommentsDTO result = sheetCommentsService.save(sheetCommentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sheetCommentsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /sheet-comments} : get all the sheetComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sheetComments in body.
     */
    @GetMapping("/sheet-comments")
    public ResponseEntity<List<SheetCommentsDTO>> getAllSheetComments(Pageable pageable) {
        log.debug("REST request to get a page of SheetComments");
        Page<SheetCommentsDTO> page = sheetCommentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sheet-comments/:id} : get the "id" sheetComments.
     *
     * @param id the id of the sheetCommentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sheetCommentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sheet-comments/{id}")
    public ResponseEntity<SheetCommentsDTO> getSheetComments(@PathVariable String id) {
        log.debug("REST request to get SheetComments : {}", id);
        Optional<SheetCommentsDTO> sheetCommentsDTO = sheetCommentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sheetCommentsDTO);
    }

    /**
     * {@code DELETE  /sheet-comments/:id} : delete the "id" sheetComments.
     *
     * @param id the id of the sheetCommentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sheet-comments/{id}")
    public ResponseEntity<Void> deleteSheetComments(@PathVariable String id) {
        log.debug("REST request to delete SheetComments : {}", id);
        sheetCommentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/sheet-comments?query=:query} : search for the sheetComments corresponding
     * to the query.
     *
     * @param query the query of the sheetComments search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/sheet-comments")
    public ResponseEntity<List<SheetCommentsDTO>> searchSheetComments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SheetComments for query {}", query);
        Page<SheetCommentsDTO> page = sheetCommentsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
