package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.TaskTypesService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.TaskTypesDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.TaskTypes}.
 */
@RestController
@RequestMapping("/api")
public class TaskTypesResource {

    private final Logger log = LoggerFactory.getLogger(TaskTypesResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceTaskTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskTypesService taskTypesService;

    public TaskTypesResource(TaskTypesService taskTypesService) {
        this.taskTypesService = taskTypesService;
    }

    /**
     * {@code POST  /task-types} : Create a new taskTypes.
     *
     * @param taskTypesDTO the taskTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskTypesDTO, or with status {@code 400 (Bad Request)} if the taskTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-types")
    public ResponseEntity<TaskTypesDTO> createTaskTypes(@Valid @RequestBody TaskTypesDTO taskTypesDTO) throws URISyntaxException {
        log.debug("REST request to save TaskTypes : {}", taskTypesDTO);
        if (taskTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskTypesDTO result = taskTypesService.save(taskTypesDTO);
        return ResponseEntity.created(new URI("/api/task-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /task-types} : Updates an existing taskTypes.
     *
     * @param taskTypesDTO the taskTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskTypesDTO,
     * or with status {@code 400 (Bad Request)} if the taskTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-types")
    public ResponseEntity<TaskTypesDTO> updateTaskTypes(@Valid @RequestBody TaskTypesDTO taskTypesDTO) throws URISyntaxException {
        log.debug("REST request to update TaskTypes : {}", taskTypesDTO);
        if (taskTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskTypesDTO result = taskTypesService.save(taskTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskTypesDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /task-types} : get all the taskTypes.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskTypes in body.
     */
    @GetMapping("/task-types")
    public ResponseEntity<List<TaskTypesDTO>> getAllTaskTypes(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("task-is-null".equals(filter)) {
            log.debug("REST request to get all TaskTypess where task is null");
            return new ResponseEntity<>(taskTypesService.findAllWhereTaskIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of TaskTypes");
        Page<TaskTypesDTO> page = taskTypesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-types/:id} : get the "id" taskTypes.
     *
     * @param id the id of the taskTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-types/{id}")
    public ResponseEntity<TaskTypesDTO> getTaskTypes(@PathVariable String id) {
        log.debug("REST request to get TaskTypes : {}", id);
        Optional<TaskTypesDTO> taskTypesDTO = taskTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskTypesDTO);
    }

    /**
     * {@code DELETE  /task-types/:id} : delete the "id" taskTypes.
     *
     * @param id the id of the taskTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-types/{id}")
    public ResponseEntity<Void> deleteTaskTypes(@PathVariable String id) {
        log.debug("REST request to delete TaskTypes : {}", id);
        taskTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/task-types?query=:query} : search for the taskTypes corresponding
     * to the query.
     *
     * @param query the query of the taskTypes search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/task-types")
    public ResponseEntity<List<TaskTypesDTO>> searchTaskTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaskTypes for query {}", query);
        Page<TaskTypesDTO> page = taskTypesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
