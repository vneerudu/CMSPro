package com.cmspro.basemicroservice.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cmspro.basemicroservice.domain.ProjectStatus;
import com.cmspro.basemicroservice.domain.*; // for static metamodels
import com.cmspro.basemicroservice.repository.ProjectStatusRepository;
import com.cmspro.basemicroservice.repository.search.ProjectStatusSearchRepository;
import com.cmspro.basemicroservice.service.dto.ProjectStatusCriteria;
import com.cmspro.basemicroservice.service.dto.ProjectStatusDTO;
import com.cmspro.basemicroservice.service.mapper.ProjectStatusMapper;

/**
 * Service for executing complex queries for {@link ProjectStatus} entities in the database.
 * The main input is a {@link ProjectStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectStatusDTO} or a {@link Page} of {@link ProjectStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectStatusQueryService extends QueryService<ProjectStatus> {

    private final Logger log = LoggerFactory.getLogger(ProjectStatusQueryService.class);

    private final ProjectStatusRepository projectStatusRepository;

    private final ProjectStatusMapper projectStatusMapper;

    private final ProjectStatusSearchRepository projectStatusSearchRepository;

    public ProjectStatusQueryService(ProjectStatusRepository projectStatusRepository, ProjectStatusMapper projectStatusMapper, ProjectStatusSearchRepository projectStatusSearchRepository) {
        this.projectStatusRepository = projectStatusRepository;
        this.projectStatusMapper = projectStatusMapper;
        this.projectStatusSearchRepository = projectStatusSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectStatusDTO> findByCriteria(ProjectStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectStatus> specification = createSpecification(criteria);
        return projectStatusMapper.toDto(projectStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProjectStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectStatusDTO> findByCriteria(ProjectStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectStatus> specification = createSpecification(criteria);
        return projectStatusRepository.findAll(specification, page)
            .map(projectStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectStatus> specification = createSpecification(criteria);
        return projectStatusRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ProjectStatus> createSpecification(ProjectStatusCriteria criteria) {
        Specification<ProjectStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectStatus_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectStatus_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectStatus_.description));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), ProjectStatus_.isActive));
            }
        }
        return specification;
    }
}
