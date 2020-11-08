package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.TaskStatusesService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.TaskStatusesDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.TaskStatuses}.
 */
@RestController
@RequestMapping("/api")
public class TaskStatusesResource {

    private final Logger log = LoggerFactory.getLogger(TaskStatusesResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceTaskStatuses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskStatusesService taskStatusesService;

    public TaskStatusesResource(TaskStatusesService taskStatusesService) {
        this.taskStatusesService = taskStatusesService;
    }

    /**
     * {@code POST  /task-statuses} : Create a new taskStatuses.
     *
     * @param taskStatusesDTO the taskStatusesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskStatusesDTO, or with status {@code 400 (Bad Request)} if the taskStatuses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-statuses")
    public ResponseEntity<TaskStatusesDTO> createTaskStatuses(@Valid @RequestBody TaskStatusesDTO taskStatusesDTO) throws URISyntaxException {
        log.debug("REST request to save TaskStatuses : {}", taskStatusesDTO);
        if (taskStatusesDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskStatuses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskStatusesDTO result = taskStatusesService.save(taskStatusesDTO);
        return ResponseEntity.created(new URI("/api/task-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /task-statuses} : Updates an existing taskStatuses.
     *
     * @param taskStatusesDTO the taskStatusesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskStatusesDTO,
     * or with status {@code 400 (Bad Request)} if the taskStatusesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskStatusesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-statuses")
    public ResponseEntity<TaskStatusesDTO> updateTaskStatuses(@Valid @RequestBody TaskStatusesDTO taskStatusesDTO) throws URISyntaxException {
        log.debug("REST request to update TaskStatuses : {}", taskStatusesDTO);
        if (taskStatusesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskStatusesDTO result = taskStatusesService.save(taskStatusesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskStatusesDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /task-statuses} : get all the taskStatuses.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskStatuses in body.
     */
    @GetMapping("/task-statuses")
    public ResponseEntity<List<TaskStatusesDTO>> getAllTaskStatuses(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("task-is-null".equals(filter)) {
            log.debug("REST request to get all TaskStatusess where task is null");
            return new ResponseEntity<>(taskStatusesService.findAllWhereTaskIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of TaskStatuses");
        Page<TaskStatusesDTO> page = taskStatusesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-statuses/:id} : get the "id" taskStatuses.
     *
     * @param id the id of the taskStatusesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskStatusesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-statuses/{id}")
    public ResponseEntity<TaskStatusesDTO> getTaskStatuses(@PathVariable String id) {
        log.debug("REST request to get TaskStatuses : {}", id);
        Optional<TaskStatusesDTO> taskStatusesDTO = taskStatusesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskStatusesDTO);
    }

    /**
     * {@code DELETE  /task-statuses/:id} : delete the "id" taskStatuses.
     *
     * @param id the id of the taskStatusesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-statuses/{id}")
    public ResponseEntity<Void> deleteTaskStatuses(@PathVariable String id) {
        log.debug("REST request to delete TaskStatuses : {}", id);
        taskStatusesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/task-statuses?query=:query} : search for the taskStatuses corresponding
     * to the query.
     *
     * @param query the query of the taskStatuses search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/task-statuses")
    public ResponseEntity<List<TaskStatusesDTO>> searchTaskStatuses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaskStatuses for query {}", query);
        Page<TaskStatusesDTO> page = taskStatusesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
