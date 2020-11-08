package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.RootCauses;
import com.cmspro.base.microservice.repository.RootCausesRepository;
import com.cmspro.base.microservice.repository.search.RootCausesSearchRepository;
import com.cmspro.base.microservice.service.dto.RootCausesDTO;
import com.cmspro.base.microservice.service.mapper.RootCausesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RootCauses}.
 */
@Service
public class RootCausesService {

    private final Logger log = LoggerFactory.getLogger(RootCausesService.class);

    private final RootCausesRepository rootCausesRepository;

    private final RootCausesMapper rootCausesMapper;

    private final RootCausesSearchRepository rootCausesSearchRepository;

    public RootCausesService(RootCausesRepository rootCausesRepository, RootCausesMapper rootCausesMapper, RootCausesSearchRepository rootCausesSearchRepository) {
        this.rootCausesRepository = rootCausesRepository;
        this.rootCausesMapper = rootCausesMapper;
        this.rootCausesSearchRepository = rootCausesSearchRepository;
    }

    /**
     * Save a rootCauses.
     *
     * @param rootCausesDTO the entity to save.
     * @return the persisted entity.
     */
    public RootCausesDTO save(RootCausesDTO rootCausesDTO) {
        log.debug("Request to save RootCauses : {}", rootCausesDTO);
        RootCauses rootCauses = rootCausesMapper.toEntity(rootCausesDTO);
        rootCauses = rootCausesRepository.save(rootCauses);
        RootCausesDTO result = rootCausesMapper.toDto(rootCauses);
        rootCausesSearchRepository.save(rootCauses);
        return result;
    }

    /**
     * Get all the rootCauses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RootCausesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RootCauses");
        return rootCausesRepository.findAll(pageable)
            .map(rootCausesMapper::toDto);
    }



    /**
     *  Get all the rootCauses where Task is {@code null}.
     *  @return the list of entities.
     */
    public List<RootCausesDTO> findAllWhereTaskIsNull() {
        log.debug("Request to get all rootCauses where Task is null");
        return StreamSupport
            .stream(rootCausesRepository.findAll().spliterator(), false)
            .filter(rootCauses -> rootCauses.getTask() == null)
            .map(rootCausesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one rootCauses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RootCausesDTO> findOne(String id) {
        log.debug("Request to get RootCauses : {}", id);
        return rootCausesRepository.findById(id)
            .map(rootCausesMapper::toDto);
    }

    /**
     * Delete the rootCauses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete RootCauses : {}", id);
        rootCausesRepository.deleteById(id);
        rootCausesSearchRepository.deleteById(id);
    }

    /**
     * Search for the rootCauses corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RootCausesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RootCauses for query {}", query);
        return rootCausesSearchRepository.search(queryStringQuery(query), pageable)
            .map(rootCausesMapper::toDto);
    }
}
