package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.Projects;
import com.cmspro.base.microservice.repository.ProjectsRepository;
import com.cmspro.base.microservice.repository.search.ProjectsSearchRepository;
import com.cmspro.base.microservice.service.dto.ProjectsDTO;
import com.cmspro.base.microservice.service.mapper.ProjectsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Projects}.
 */
@Service
public class ProjectsService {

    private final Logger log = LoggerFactory.getLogger(ProjectsService.class);

    private final ProjectsRepository projectsRepository;

    private final ProjectsMapper projectsMapper;

    private final ProjectsSearchRepository projectsSearchRepository;

    public ProjectsService(ProjectsRepository projectsRepository, ProjectsMapper projectsMapper, ProjectsSearchRepository projectsSearchRepository) {
        this.projectsRepository = projectsRepository;
        this.projectsMapper = projectsMapper;
        this.projectsSearchRepository = projectsSearchRepository;
    }

    /**
     * Save a projects.
     *
     * @param projectsDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectsDTO save(ProjectsDTO projectsDTO) {
        log.debug("Request to save Projects : {}", projectsDTO);
        Projects projects = projectsMapper.toEntity(projectsDTO);
        projects = projectsRepository.save(projects);
        ProjectsDTO result = projectsMapper.toDto(projects);
        projectsSearchRepository.save(projects);
        return result;
    }

    /**
     * Get all the projects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ProjectsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        return projectsRepository.findAll(pageable)
            .map(projectsMapper::toDto);
    }


    /**
     * Get one projects by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ProjectsDTO> findOne(String id) {
        log.debug("Request to get Projects : {}", id);
        return projectsRepository.findById(id)
            .map(projectsMapper::toDto);
    }

    /**
     * Delete the projects by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Projects : {}", id);
        projectsRepository.deleteById(id);
        projectsSearchRepository.deleteById(id);
    }

    /**
     * Search for the projects corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ProjectsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Projects for query {}", query);
        return projectsSearchRepository.search(queryStringQuery(query), pageable)
            .map(projectsMapper::toDto);
    }
}
