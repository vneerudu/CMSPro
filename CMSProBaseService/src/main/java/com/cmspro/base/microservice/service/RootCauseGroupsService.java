package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.RootCauseGroups;
import com.cmspro.base.microservice.repository.RootCauseGroupsRepository;
import com.cmspro.base.microservice.repository.search.RootCauseGroupsSearchRepository;
import com.cmspro.base.microservice.service.dto.RootCauseGroupsDTO;
import com.cmspro.base.microservice.service.mapper.RootCauseGroupsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RootCauseGroups}.
 */
@Service
public class RootCauseGroupsService {

    private final Logger log = LoggerFactory.getLogger(RootCauseGroupsService.class);

    private final RootCauseGroupsRepository rootCauseGroupsRepository;

    private final RootCauseGroupsMapper rootCauseGroupsMapper;

    private final RootCauseGroupsSearchRepository rootCauseGroupsSearchRepository;

    public RootCauseGroupsService(RootCauseGroupsRepository rootCauseGroupsRepository, RootCauseGroupsMapper rootCauseGroupsMapper, RootCauseGroupsSearchRepository rootCauseGroupsSearchRepository) {
        this.rootCauseGroupsRepository = rootCauseGroupsRepository;
        this.rootCauseGroupsMapper = rootCauseGroupsMapper;
        this.rootCauseGroupsSearchRepository = rootCauseGroupsSearchRepository;
    }

    /**
     * Save a rootCauseGroups.
     *
     * @param rootCauseGroupsDTO the entity to save.
     * @return the persisted entity.
     */
    public RootCauseGroupsDTO save(RootCauseGroupsDTO rootCauseGroupsDTO) {
        log.debug("Request to save RootCauseGroups : {}", rootCauseGroupsDTO);
        RootCauseGroups rootCauseGroups = rootCauseGroupsMapper.toEntity(rootCauseGroupsDTO);
        rootCauseGroups = rootCauseGroupsRepository.save(rootCauseGroups);
        RootCauseGroupsDTO result = rootCauseGroupsMapper.toDto(rootCauseGroups);
        rootCauseGroupsSearchRepository.save(rootCauseGroups);
        return result;
    }

    /**
     * Get all the rootCauseGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RootCauseGroupsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RootCauseGroups");
        return rootCauseGroupsRepository.findAll(pageable)
            .map(rootCauseGroupsMapper::toDto);
    }


    /**
     * Get one rootCauseGroups by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RootCauseGroupsDTO> findOne(String id) {
        log.debug("Request to get RootCauseGroups : {}", id);
        return rootCauseGroupsRepository.findById(id)
            .map(rootCauseGroupsMapper::toDto);
    }

    /**
     * Delete the rootCauseGroups by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete RootCauseGroups : {}", id);
        rootCauseGroupsRepository.deleteById(id);
        rootCauseGroupsSearchRepository.deleteById(id);
    }

    /**
     * Search for the rootCauseGroups corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RootCauseGroupsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RootCauseGroups for query {}", query);
        return rootCauseGroupsSearchRepository.search(queryStringQuery(query), pageable)
            .map(rootCauseGroupsMapper::toDto);
    }
}
