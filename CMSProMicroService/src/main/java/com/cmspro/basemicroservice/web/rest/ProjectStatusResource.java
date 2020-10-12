package com.cmspro.basemicroservice.web.rest;

import com.cmspro.basemicroservice.service.ProjectStatusService;
import com.cmspro.basemicroservice.web.rest.errors.BadRequestAlertException;
import com.cmspro.basemicroservice.service.dto.ProjectStatusDTO;
import com.cmspro.basemicroservice.service.dto.ProjectStatusCriteria;
import com.cmspro.basemicroservice.service.ProjectStatusQueryService;

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
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
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
 * REST controller for managing {@link com.cmspro.basemicroservice.domain.ProjectStatus}.
 */
@RestController
@RequestMapping("/api")
public class ProjectStatusResource {

    private final Logger log = LoggerFactory.getLogger(ProjectStatusResource.class);

    private static final String ENTITY_NAME = "cmsProMicroServiceProjectStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectStatusService projectStatusService;

    private final ProjectStatusQueryService projectStatusQueryService;

    public ProjectStatusResource(ProjectStatusService projectStatusService, ProjectStatusQueryService projectStatusQueryService) {
        this.projectStatusService = projectStatusService;
        this.projectStatusQueryService = projectStatusQueryService;
    }

    /**
     * {@code POST  /project-statuses} : Create a new projectStatus.
     *
     * @param projectStatusDTO the projectStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectStatusDTO, or with status {@code 400 (Bad Request)} if the projectStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-statuses")
    public ResponseEntity<ProjectStatusDTO> createProjectStatus(@Valid @RequestBody ProjectStatusDTO projectStatusDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectStatus : {}", projectStatusDTO);
        if (projectStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectStatusDTO result = projectStatusService.save(projectStatusDTO);
        return ResponseEntity.created(new URI("/api/project-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-statuses} : Updates an existing projectStatus.
     *
     * @param projectStatusDTO the projectStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectStatusDTO,
     * or with status {@code 400 (Bad Request)} if the projectStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-statuses")
    public ResponseEntity<ProjectStatusDTO> updateProjectStatus(@Valid @RequestBody ProjectStatusDTO projectStatusDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectStatus : {}", projectStatusDTO);
        if (projectStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectStatusDTO result = projectStatusService.save(projectStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-statuses} : get all the projectStatuses.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectStatuses in body.
     */
    @GetMapping("/project-statuses")
    public ResponseEntity<List<ProjectStatusDTO>> getAllProjectStatuses(ProjectStatusCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ProjectStatuses by criteria: {}", criteria);
        Page<ProjectStatusDTO> page = projectStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /project-statuses/count} : count all the projectStatuses.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/project-statuses/count")
    public ResponseEntity<Long> countProjectStatuses(ProjectStatusCriteria criteria) {
        log.debug("REST request to count ProjectStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-statuses/:id} : get the "id" projectStatus.
     *
     * @param id the id of the projectStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-statuses/{id}")
    public ResponseEntity<ProjectStatusDTO> getProjectStatus(@PathVariable Long id) {
        log.debug("REST request to get ProjectStatus : {}", id);
        Optional<ProjectStatusDTO> projectStatusDTO = projectStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectStatusDTO);
    }

    /**
     * {@code DELETE  /project-statuses/:id} : delete the "id" projectStatus.
     *
     * @param id the id of the projectStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-statuses/{id}")
    public ResponseEntity<Void> deleteProjectStatus(@PathVariable Long id) {
        log.debug("REST request to delete ProjectStatus : {}", id);
        projectStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/project-statuses?query=:query} : search for the projectStatus corresponding
     * to the query.
     *
     * @param query the query of the projectStatus search.
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the result of the search.
     */
    @GetMapping("/_search/project-statuses")
    public ResponseEntity<List<ProjectStatusDTO>> searchProjectStatuses(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of ProjectStatuses for query {}", query);
        Page<ProjectStatusDTO> page = projectStatusService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
