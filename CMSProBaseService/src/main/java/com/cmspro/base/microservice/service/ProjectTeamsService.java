package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.ProjectTeams;
import com.cmspro.base.microservice.repository.ProjectTeamsRepository;
import com.cmspro.base.microservice.repository.search.ProjectTeamsSearchRepository;
import com.cmspro.base.microservice.service.dto.ProjectTeamsDTO;
import com.cmspro.base.microservice.service.mapper.ProjectTeamsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ProjectTeams}.
 */
@Service
public class ProjectTeamsService {

    private final Logger log = LoggerFactory.getLogger(ProjectTeamsService.class);

    private final ProjectTeamsRepository projectTeamsRepository;

    private final ProjectTeamsMapper projectTeamsMapper;

    private final ProjectTeamsSearchRepository projectTeamsSearchRepository;

    public ProjectTeamsService(ProjectTeamsRepository projectTeamsRepository, ProjectTeamsMapper projectTeamsMapper, ProjectTeamsSearchRepository projectTeamsSearchRepository) {
        this.projectTeamsRepository = projectTeamsRepository;
        this.projectTeamsMapper = projectTeamsMapper;
        this.projectTeamsSearchRepository = projectTeamsSearchRepository;
    }

    /**
     * Save a projectTeams.
     *
     * @param projectTeamsDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectTeamsDTO save(ProjectTeamsDTO projectTeamsDTO) {
        log.debug("Request to save ProjectTeams : {}", projectTeamsDTO);
        ProjectTeams projectTeams = projectTeamsMapper.toEntity(projectTeamsDTO);
        projectTeams = projectTeamsRepository.save(projectTeams);
        ProjectTeamsDTO result = projectTeamsMapper.toDto(projectTeams);
        projectTeamsSearchRepository.save(projectTeams);
        return result;
    }

    /**
     * Get all the projectTeams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ProjectTeamsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectTeams");
        return projectTeamsRepository.findAll(pageable)
            .map(projectTeamsMapper::toDto);
    }


    /**
     * Get one projectTeams by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ProjectTeamsDTO> findOne(String id) {
        log.debug("Request to get ProjectTeams : {}", id);
        return projectTeamsRepository.findById(id)
            .map(projectTeamsMapper::toDto);
    }

    /**
     * Delete the projectTeams by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ProjectTeams : {}", id);
        projectTeamsRepository.deleteById(id);
        projectTeamsSearchRepository.deleteById(id);
    }

    /**
     * Search for the projectTeams corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ProjectTeamsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProjectTeams for query {}", query);
        return projectTeamsSearchRepository.search(queryStringQuery(query), pageable)
            .map(projectTeamsMapper::toDto);
    }
}
