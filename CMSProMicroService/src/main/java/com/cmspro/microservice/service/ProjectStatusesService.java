package com.cmspro.microservice.service;

import com.cmspro.microservice.domain.ProjectStatuses;
import com.cmspro.microservice.repository.ProjectStatusesRepository;
import com.cmspro.microservice.repository.search.ProjectStatusesSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ProjectStatuses}.
 */
@Service
@Transactional
public class ProjectStatusesService {

    private final Logger log = LoggerFactory.getLogger(ProjectStatusesService.class);

    private final ProjectStatusesRepository projectStatusesRepository;

    private final ProjectStatusesSearchRepository projectStatusesSearchRepository;

    public ProjectStatusesService(ProjectStatusesRepository projectStatusesRepository, ProjectStatusesSearchRepository projectStatusesSearchRepository) {
        this.projectStatusesRepository = projectStatusesRepository;
        this.projectStatusesSearchRepository = projectStatusesSearchRepository;
    }

    /**
     * Save a projectStatuses.
     *
     * @param projectStatuses the entity to save.
     * @return the persisted entity.
     */
    public ProjectStatuses save(ProjectStatuses projectStatuses) {
        log.debug("Request to save ProjectStatuses : {}", projectStatuses);
        ProjectStatuses result = projectStatusesRepository.save(projectStatuses);
        projectStatusesSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the projectStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectStatuses> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectStatuses");
        return projectStatusesRepository.findAll(pageable);
    }


    /**
     * Get one projectStatuses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectStatuses> findOne(Long id) {
        log.debug("Request to get ProjectStatuses : {}", id);
        return projectStatusesRepository.findById(id);
    }

    /**
     * Delete the projectStatuses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectStatuses : {}", id);
        projectStatusesRepository.deleteById(id);
        projectStatusesSearchRepository.deleteById(id);
    }

    /**
     * Search for the projectStatuses corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectStatuses> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProjectStatuses for query {}", query);
        return projectStatusesSearchRepository.search(queryStringQuery(query), pageable);    }
}
