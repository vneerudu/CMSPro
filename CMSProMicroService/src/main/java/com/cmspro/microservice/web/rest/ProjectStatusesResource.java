package com.cmspro.microservice.web.rest;

import com.cmspro.microservice.domain.ProjectStatuses;
import com.cmspro.microservice.service.ProjectStatusesService;
import com.cmspro.microservice.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.cmspro.microservice.domain.ProjectStatuses}.
 */
@RestController
@RequestMapping("/api")
public class ProjectStatusesResource {

    private final Logger log = LoggerFactory.getLogger(ProjectStatusesResource.class);

    private static final String ENTITY_NAME = "cmsProMicroServiceProjectStatuses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectStatusesService projectStatusesService;

    public ProjectStatusesResource(ProjectStatusesService projectStatusesService) {
        this.projectStatusesService = projectStatusesService;
    }

    /**
     * {@code POST  /project-statuses} : Create a new projectStatuses.
     *
     * @param projectStatuses the projectStatuses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectStatuses, or with status {@code 400 (Bad Request)} if the projectStatuses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-statuses")
    public ResponseEntity<ProjectStatuses> createProjectStatuses(@Valid @RequestBody ProjectStatuses projectStatuses) throws URISyntaxException {
        log.debug("REST request to save ProjectStatuses : {}", projectStatuses);
        if (projectStatuses.getId() != null) {
            throw new BadRequestAlertException("A new projectStatuses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectStatuses result = projectStatusesService.save(projectStatuses);
        return ResponseEntity.created(new URI("/api/project-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-statuses} : Updates an existing projectStatuses.
     *
     * @param projectStatuses the projectStatuses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectStatuses,
     * or with status {@code 400 (Bad Request)} if the projectStatuses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectStatuses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-statuses")
    public ResponseEntity<ProjectStatuses> updateProjectStatuses(@Valid @RequestBody ProjectStatuses projectStatuses) throws URISyntaxException {
        log.debug("REST request to update ProjectStatuses : {}", projectStatuses);
        if (projectStatuses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectStatuses result = projectStatusesService.save(projectStatuses);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectStatuses.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-statuses} : get all the projectStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectStatuses in body.
     */
    @GetMapping("/project-statuses")
    public ResponseEntity<List<ProjectStatuses>> getAllProjectStatuses(Pageable pageable) {
        log.debug("REST request to get a page of ProjectStatuses");
        Page<ProjectStatuses> page = projectStatusesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-statuses/:id} : get the "id" projectStatuses.
     *
     * @param id the id of the projectStatuses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectStatuses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-statuses/{id}")
    public ResponseEntity<ProjectStatuses> getProjectStatuses(@PathVariable Long id) {
        log.debug("REST request to get ProjectStatuses : {}", id);
        Optional<ProjectStatuses> projectStatuses = projectStatusesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectStatuses);
    }

    /**
     * {@code DELETE  /project-statuses/:id} : delete the "id" projectStatuses.
     *
     * @param id the id of the projectStatuses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-statuses/{id}")
    public ResponseEntity<Void> deleteProjectStatuses(@PathVariable Long id) {
        log.debug("REST request to delete ProjectStatuses : {}", id);
        projectStatusesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/project-statuses?query=:query} : search for the projectStatuses corresponding
     * to the query.
     *
     * @param query the query of the projectStatuses search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/project-statuses")
    public ResponseEntity<List<ProjectStatuses>> searchProjectStatuses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProjectStatuses for query {}", query);
        Page<ProjectStatuses> page = projectStatusesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
