package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.SheetTagsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.SheetTagsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.SheetTags}.
 */
@RestController
@RequestMapping("/api")
public class SheetTagsResource {

    private final Logger log = LoggerFactory.getLogger(SheetTagsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceSheetTags";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SheetTagsService sheetTagsService;

    public SheetTagsResource(SheetTagsService sheetTagsService) {
        this.sheetTagsService = sheetTagsService;
    }

    /**
     * {@code POST  /sheet-tags} : Create a new sheetTags.
     *
     * @param sheetTagsDTO the sheetTagsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sheetTagsDTO, or with status {@code 400 (Bad Request)} if the sheetTags has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sheet-tags")
    public ResponseEntity<SheetTagsDTO> createSheetTags(@Valid @RequestBody SheetTagsDTO sheetTagsDTO) throws URISyntaxException {
        log.debug("REST request to save SheetTags : {}", sheetTagsDTO);
        if (sheetTagsDTO.getId() != null) {
            throw new BadRequestAlertException("A new sheetTags cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SheetTagsDTO result = sheetTagsService.save(sheetTagsDTO);
        return ResponseEntity.created(new URI("/api/sheet-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /sheet-tags} : Updates an existing sheetTags.
     *
     * @param sheetTagsDTO the sheetTagsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sheetTagsDTO,
     * or with status {@code 400 (Bad Request)} if the sheetTagsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sheetTagsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sheet-tags")
    public ResponseEntity<SheetTagsDTO> updateSheetTags(@Valid @RequestBody SheetTagsDTO sheetTagsDTO) throws URISyntaxException {
        log.debug("REST request to update SheetTags : {}", sheetTagsDTO);
        if (sheetTagsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SheetTagsDTO result = sheetTagsService.save(sheetTagsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sheetTagsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /sheet-tags} : get all the sheetTags.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sheetTags in body.
     */
    @GetMapping("/sheet-tags")
    public ResponseEntity<List<SheetTagsDTO>> getAllSheetTags(Pageable pageable) {
        log.debug("REST request to get a page of SheetTags");
        Page<SheetTagsDTO> page = sheetTagsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sheet-tags/:id} : get the "id" sheetTags.
     *
     * @param id the id of the sheetTagsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sheetTagsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sheet-tags/{id}")
    public ResponseEntity<SheetTagsDTO> getSheetTags(@PathVariable String id) {
        log.debug("REST request to get SheetTags : {}", id);
        Optional<SheetTagsDTO> sheetTagsDTO = sheetTagsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sheetTagsDTO);
    }

    /**
     * {@code DELETE  /sheet-tags/:id} : delete the "id" sheetTags.
     *
     * @param id the id of the sheetTagsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sheet-tags/{id}")
    public ResponseEntity<Void> deleteSheetTags(@PathVariable String id) {
        log.debug("REST request to delete SheetTags : {}", id);
        sheetTagsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/sheet-tags?query=:query} : search for the sheetTags corresponding
     * to the query.
     *
     * @param query the query of the sheetTags search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/sheet-tags")
    public ResponseEntity<List<SheetTagsDTO>> searchSheetTags(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SheetTags for query {}", query);
        Page<SheetTagsDTO> page = sheetTagsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
