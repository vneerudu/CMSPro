package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.ListsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.ListsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.Lists}.
 */
@RestController
@RequestMapping("/api")
public class ListsResource {

    private final Logger log = LoggerFactory.getLogger(ListsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceLists";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListsService listsService;

    public ListsResource(ListsService listsService) {
        this.listsService = listsService;
    }

    /**
     * {@code POST  /lists} : Create a new lists.
     *
     * @param listsDTO the listsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listsDTO, or with status {@code 400 (Bad Request)} if the lists has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lists")
    public ResponseEntity<ListsDTO> createLists(@Valid @RequestBody ListsDTO listsDTO) throws URISyntaxException {
        log.debug("REST request to save Lists : {}", listsDTO);
        if (listsDTO.getId() != null) {
            throw new BadRequestAlertException("A new lists cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListsDTO result = listsService.save(listsDTO);
        return ResponseEntity.created(new URI("/api/lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /lists} : Updates an existing lists.
     *
     * @param listsDTO the listsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listsDTO,
     * or with status {@code 400 (Bad Request)} if the listsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lists")
    public ResponseEntity<ListsDTO> updateLists(@Valid @RequestBody ListsDTO listsDTO) throws URISyntaxException {
        log.debug("REST request to update Lists : {}", listsDTO);
        if (listsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListsDTO result = listsService.save(listsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, listsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /lists} : get all the lists.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lists in body.
     */
    @GetMapping("/lists")
    public ResponseEntity<List<ListsDTO>> getAllLists(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("task-is-null".equals(filter)) {
            log.debug("REST request to get all Listss where task is null");
            return new ResponseEntity<>(listsService.findAllWhereTaskIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Lists");
        Page<ListsDTO> page = listsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lists/:id} : get the "id" lists.
     *
     * @param id the id of the listsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lists/{id}")
    public ResponseEntity<ListsDTO> getLists(@PathVariable String id) {
        log.debug("REST request to get Lists : {}", id);
        Optional<ListsDTO> listsDTO = listsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listsDTO);
    }

    /**
     * {@code DELETE  /lists/:id} : delete the "id" lists.
     *
     * @param id the id of the listsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lists/{id}")
    public ResponseEntity<Void> deleteLists(@PathVariable String id) {
        log.debug("REST request to delete Lists : {}", id);
        listsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/lists?query=:query} : search for the lists corresponding
     * to the query.
     *
     * @param query the query of the lists search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/lists")
    public ResponseEntity<List<ListsDTO>> searchLists(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Lists for query {}", query);
        Page<ListsDTO> page = listsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
