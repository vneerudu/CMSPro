package com.cmspro.base.microservice.web.rest;

import com.cmspro.base.microservice.service.TaskAttachmentOthersService;
import com.cmspro.base.microservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.base.microservice.service.dto.TaskAttachmentOthersDTO;

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
 * REST controller for managing {@link com.cmspro.base.microservice.domain.TaskAttachmentOthers}.
 */
@RestController
@RequestMapping("/api")
public class TaskAttachmentOthersResource {

    private final Logger log = LoggerFactory.getLogger(TaskAttachmentOthersResource.class);

    private static final String ENTITY_NAME = "cmsProBaseServiceTaskAttachmentOthers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskAttachmentOthersService taskAttachmentOthersService;

    public TaskAttachmentOthersResource(TaskAttachmentOthersService taskAttachmentOthersService) {
        this.taskAttachmentOthersService = taskAttachmentOthersService;
    }

    /**
     * {@code POST  /task-attachment-others} : Create a new taskAttachmentOthers.
     *
     * @param taskAttachmentOthersDTO the taskAttachmentOthersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskAttachmentOthersDTO, or with status {@code 400 (Bad Request)} if the taskAttachmentOthers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-attachment-others")
    public ResponseEntity<TaskAttachmentOthersDTO> createTaskAttachmentOthers(@Valid @RequestBody TaskAttachmentOthersDTO taskAttachmentOthersDTO) throws URISyntaxException {
        log.debug("REST request to save TaskAttachmentOthers : {}", taskAttachmentOthersDTO);
        if (taskAttachmentOthersDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskAttachmentOthers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskAttachmentOthersDTO result = taskAttachmentOthersService.save(taskAttachmentOthersDTO);
        return ResponseEntity.created(new URI("/api/task-attachment-others/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /task-attachment-others} : Updates an existing taskAttachmentOthers.
     *
     * @param taskAttachmentOthersDTO the taskAttachmentOthersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskAttachmentOthersDTO,
     * or with status {@code 400 (Bad Request)} if the taskAttachmentOthersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskAttachmentOthersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-attachment-others")
    public ResponseEntity<TaskAttachmentOthersDTO> updateTaskAttachmentOthers(@Valid @RequestBody TaskAttachmentOthersDTO taskAttachmentOthersDTO) throws URISyntaxException {
        log.debug("REST request to update TaskAttachmentOthers : {}", taskAttachmentOthersDTO);
        if (taskAttachmentOthersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskAttachmentOthersDTO result = taskAttachmentOthersService.save(taskAttachmentOthersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskAttachmentOthersDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /task-attachment-others} : get all the taskAttachmentOthers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskAttachmentOthers in body.
     */
    @GetMapping("/task-attachment-others")
    public ResponseEntity<List<TaskAttachmentOthersDTO>> getAllTaskAttachmentOthers(Pageable pageable) {
        log.debug("REST request to get a page of TaskAttachmentOthers");
        Page<TaskAttachmentOthersDTO> page = taskAttachmentOthersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-attachment-others/:id} : get the "id" taskAttachmentOthers.
     *
     * @param id the id of the taskAttachmentOthersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskAttachmentOthersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-attachment-others/{id}")
    public ResponseEntity<TaskAttachmentOthersDTO> getTaskAttachmentOthers(@PathVariable String id) {
        log.debug("REST request to get TaskAttachmentOthers : {}", id);
        Optional<TaskAttachmentOthersDTO> taskAttachmentOthersDTO = taskAttachmentOthersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskAttachmentOthersDTO);
    }

    /**
     * {@code DELETE  /task-attachment-others/:id} : delete the "id" taskAttachmentOthers.
     *
     * @param id the id of the taskAttachmentOthersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-attachment-others/{id}")
    public ResponseEntity<Void> deleteTaskAttachmentOthers(@PathVariable String id) {
        log.debug("REST request to delete TaskAttachmentOthers : {}", id);
        taskAttachmentOthersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/task-attachment-others?query=:query} : search for the taskAttachmentOthers corresponding
     * to the query.
     *
     * @param query the query of the taskAttachmentOthers search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/task-attachment-others")
    public ResponseEntity<List<TaskAttachmentOthersDTO>> searchTaskAttachmentOthers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaskAttachmentOthers for query {}", query);
        Page<TaskAttachmentOthersDTO> page = taskAttachmentOthersService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
