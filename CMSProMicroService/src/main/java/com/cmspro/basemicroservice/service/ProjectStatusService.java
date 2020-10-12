package com.cmspro.basemicroservice.service;

import com.cmspro.basemicroservice.domain.ProjectStatus;
import com.cmspro.basemicroservice.repository.ProjectStatusRepository;
import com.cmspro.basemicroservice.repository.search.ProjectStatusSearchRepository;
import com.cmspro.basemicroservice.service.dto.ProjectStatusDTO;
import com.cmspro.basemicroservice.service.mapper.ProjectStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ProjectStatus}.
 */
@Service
@Transactional
public class ProjectStatusService {

    private final Logger log = LoggerFactory.getLogger(ProjectStatusService.class);

    private final ProjectStatusRepository projectStatusRepository;

    private final ProjectStatusMapper projectStatusMapper;

    private final ProjectStatusSearchRepository projectStatusSearchRepository;

    public ProjectStatusService(ProjectStatusRepository projectStatusRepository, ProjectStatusMapper projectStatusMapper, ProjectStatusSearchRepository projectStatusSearchRepository) {
        this.projectStatusRepository = projectStatusRepository;
        this.projectStatusMapper = projectStatusMapper;
        this.projectStatusSearchRepository = projectStatusSearchRepository;
    }

    /**
     * Save a projectStatus.
     *
     * @param projectStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectStatusDTO save(ProjectStatusDTO projectStatusDTO) {
        log.debug("Request to save ProjectStatus : {}", projectStatusDTO);
        ProjectStatus projectStatus = projectStatusMapper.toEntity(projectStatusDTO);
        projectStatus = projectStatusRepository.save(projectStatus);
        ProjectStatusDTO result = projectStatusMapper.toDto(projectStatus);
        projectStatusSearchRepository.save(projectStatus);
        return result;
    }

    /**
     * Get all the projectStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectStatuses");
        return projectStatusRepository.findAll(pageable)
            .map(projectStatusMapper::toDto);
    }


    /**
     * Get one projectStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectStatusDTO> findOne(Long id) {
        log.debug("Request to get ProjectStatus : {}", id);
        return projectStatusRepository.findById(id)
            .map(projectStatusMapper::toDto);
    }

    /**
     * Delete the projectStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectStatus : {}", id);
        projectStatusRepository.deleteById(id);
        projectStatusSearchRepository.deleteById(id);
    }

    /**
     * Search for the projectStatus corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectStatusDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProjectStatuses for query {}", query);
        return projectStatusSearchRepository.search(queryStringQuery(query), pageable)
            .map(projectStatusMapper::toDto);
    }
}
