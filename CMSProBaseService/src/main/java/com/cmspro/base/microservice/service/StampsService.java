package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.Stamps;
import com.cmspro.base.microservice.repository.StampsRepository;
import com.cmspro.base.microservice.repository.search.StampsSearchRepository;
import com.cmspro.base.microservice.service.dto.StampsDTO;
import com.cmspro.base.microservice.service.mapper.StampsMapper;
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
 * Service Implementation for managing {@link Stamps}.
 */
@Service
public class StampsService {

    private final Logger log = LoggerFactory.getLogger(StampsService.class);

    private final StampsRepository stampsRepository;

    private final StampsMapper stampsMapper;

    private final StampsSearchRepository stampsSearchRepository;

    public StampsService(StampsRepository stampsRepository, StampsMapper stampsMapper, StampsSearchRepository stampsSearchRepository) {
        this.stampsRepository = stampsRepository;
        this.stampsMapper = stampsMapper;
        this.stampsSearchRepository = stampsSearchRepository;
    }

    /**
     * Save a stamps.
     *
     * @param stampsDTO the entity to save.
     * @return the persisted entity.
     */
    public StampsDTO save(StampsDTO stampsDTO) {
        log.debug("Request to save Stamps : {}", stampsDTO);
        Stamps stamps = stampsMapper.toEntity(stampsDTO);
        stamps = stampsRepository.save(stamps);
        StampsDTO result = stampsMapper.toDto(stamps);
        stampsSearchRepository.save(stamps);
        return result;
    }

    /**
     * Get all the stamps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<StampsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stamps");
        return stampsRepository.findAll(pageable)
            .map(stampsMapper::toDto);
    }



    /**
     *  Get all the stamps where Task is {@code null}.
     *  @return the list of entities.
     */
    public List<StampsDTO> findAllWhereTaskIsNull() {
        log.debug("Request to get all stamps where Task is null");
        return StreamSupport
            .stream(stampsRepository.findAll().spliterator(), false)
            .filter(stamps -> stamps.getTask() == null)
            .map(stampsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one stamps by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<StampsDTO> findOne(String id) {
        log.debug("Request to get Stamps : {}", id);
        return stampsRepository.findById(id)
            .map(stampsMapper::toDto);
    }

    /**
     * Delete the stamps by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Stamps : {}", id);
        stampsRepository.deleteById(id);
        stampsSearchRepository.deleteById(id);
    }

    /**
     * Search for the stamps corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<StampsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Stamps for query {}", query);
        return stampsSearchRepository.search(queryStringQuery(query), pageable)
            .map(stampsMapper::toDto);
    }
}
