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

import com.cmspro.basemicroservice.domain.Projects;
import com.cmspro.basemicroservice.domain.*; // for static metamodels
import com.cmspro.basemicroservice.repository.ProjectsRepository;
import com.cmspro.basemicroservice.repository.search.ProjectsSearchRepository;
import com.cmspro.basemicroservice.service.dto.ProjectsCriteria;
import com.cmspro.basemicroservice.service.dto.ProjectsDTO;
import com.cmspro.basemicroservice.service.mapper.ProjectsMapper;

/**
 * Service for executing complex queries for {@link Projects} entities in the database.
 * The main input is a {@link ProjectsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectsDTO} or a {@link Page} of {@link ProjectsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectsQueryService extends QueryService<Projects> {

    private final Logger log = LoggerFactory.getLogger(ProjectsQueryService.class);

    private final ProjectsRepository projectsRepository;

    private final ProjectsMapper projectsMapper;

    private final ProjectsSearchRepository projectsSearchRepository;

    public ProjectsQueryService(ProjectsRepository projectsRepository, ProjectsMapper projectsMapper, ProjectsSearchRepository projectsSearchRepository) {
        this.projectsRepository = projectsRepository;
        this.projectsMapper = projectsMapper;
        this.projectsSearchRepository = projectsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectsDTO> findByCriteria(ProjectsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Projects> specification = createSpecification(criteria);
        return projectsMapper.toDto(projectsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProjectsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectsDTO> findByCriteria(ProjectsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Projects> specification = createSpecification(criteria);
        return projectsRepository.findAll(specification, page)
            .map(projectsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Projects> specification = createSpecification(criteria);
        return projectsRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Projects> createSpecification(ProjectsCriteria criteria) {
        Specification<Projects> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Projects_.id));
            }
            if (criteria.getProjectID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectID(), Projects_.projectID));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Projects_.name));
            }
            if (criteria.getDepartment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartment(), Projects_.department));
            }
            if (criteria.getOrganization() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganization(), Projects_.organization));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Projects_.startDate));
            }
            if (criteria.getFinishDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinishDate(), Projects_.finishDate));
            }
            if (criteria.getProjectStatusRelId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectStatusRelId(),
                    root -> root.join(Projects_.projectStatusRel, JoinType.LEFT).get(ProjectStatus_.id)));
            }
        }
        return specification;
    }
}
