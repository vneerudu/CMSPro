package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.TaskLocationsService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.TaskLocationsDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.TaskLocations}.
 */
@RestController
@RequestMapping("/api")
public class TaskLocationsResource {

    private final Logger log = LoggerFactory.getLogger(TaskLocationsResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceTaskLocations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskLocationsService taskLocationsService;

    public TaskLocationsResource(TaskLocationsService taskLocationsService) {
        this.taskLocationsService = taskLocationsService;
    }

    /**
     * {@code POST  /task-locations} : Create a new taskLocations.
     *
     * @param taskLocationsDTO the taskLocationsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskLocationsDTO, or with status {@code 400 (Bad Request)} if the taskLocations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-locations")
    public ResponseEntity<TaskLocationsDTO> createTaskLocations(@Valid @RequestBody TaskLocationsDTO taskLocationsDTO) throws URISyntaxException {
        log.debug("REST request to save TaskLocations : {}", taskLocationsDTO);
        if (taskLocationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskLocations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskLocationsDTO result = taskLocationsService.save(taskLocationsDTO);
        return ResponseEntity.created(new URI("/api/task-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /task-locations} : Updates an existing taskLocations.
     *
     * @param taskLocationsDTO the taskLocationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskLocationsDTO,
     * or with status {@code 400 (Bad Request)} if the taskLocationsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskLocationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-locations")
    public ResponseEntity<TaskLocationsDTO> updateTaskLocations(@Valid @RequestBody TaskLocationsDTO taskLocationsDTO) throws URISyntaxException {
        log.debug("REST request to update TaskLocations : {}", taskLocationsDTO);
        if (taskLocationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskLocationsDTO result = taskLocationsService.save(taskLocationsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskLocationsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /task-locations} : get all the taskLocations.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskLocations in body.
     */
    @GetMapping("/task-locations")
    public ResponseEntity<List<TaskLocationsDTO>> getAllTaskLocations(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("task-is-null".equals(filter)) {
            log.debug("REST request to get all TaskLocationss where task is null");
            return new ResponseEntity<>(taskLocationsService.findAllWhereTaskIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of TaskLocations");
        Page<TaskLocationsDTO> page = taskLocationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-locations/:id} : get the "id" taskLocations.
     *
     * @param id the id of the taskLocationsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskLocationsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-locations/{id}")
    public ResponseEntity<TaskLocationsDTO> getTaskLocations(@PathVariable String id) {
        log.debug("REST request to get TaskLocations : {}", id);
        Optional<TaskLocationsDTO> taskLocationsDTO = taskLocationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskLocationsDTO);
    }

    /**
     * {@code DELETE  /task-locations/:id} : delete the "id" taskLocations.
     *
     * @param id the id of the taskLocationsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-locations/{id}")
    public ResponseEntity<Void> deleteTaskLocations(@PathVariable String id) {
        log.debug("REST request to delete TaskLocations : {}", id);
        taskLocationsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/task-locations?query=:query} : search for the taskLocations corresponding
     * to the query.
     *
     * @param query the query of the taskLocations search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/task-locations")
    public ResponseEntity<List<TaskLocationsDTO>> searchTaskLocations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaskLocations for query {}", query);
        Page<TaskLocationsDTO> page = taskLocationsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
