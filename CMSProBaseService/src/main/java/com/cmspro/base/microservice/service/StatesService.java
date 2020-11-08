package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.States;
import com.cmspro.base.microservice.repository.StatesRepository;
import com.cmspro.base.microservice.repository.search.StatesSearchRepository;
import com.cmspro.base.microservice.service.dto.StatesDTO;
import com.cmspro.base.microservice.service.mapper.StatesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link States}.
 */
@Service
public class StatesService {

    private final Logger log = LoggerFactory.getLogger(StatesService.class);

    private final StatesRepository statesRepository;

    private final StatesMapper statesMapper;

    private final StatesSearchRepository statesSearchRepository;

    public StatesService(StatesRepository statesRepository, StatesMapper statesMapper, StatesSearchRepository statesSearchRepository) {
        this.statesRepository = statesRepository;
        this.statesMapper = statesMapper;
        this.statesSearchRepository = statesSearchRepository;
    }

    /**
     * Save a states.
     *
     * @param statesDTO the entity to save.
     * @return the persisted entity.
     */
    public StatesDTO save(StatesDTO statesDTO) {
        log.debug("Request to save States : {}", statesDTO);
        States states = statesMapper.toEntity(statesDTO);
        states = statesRepository.save(states);
        StatesDTO result = statesMapper.toDto(states);
        statesSearchRepository.save(states);
        return result;
    }

    /**
     * Get all the states.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<StatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all States");
        return statesRepository.findAll(pageable)
            .map(statesMapper::toDto);
    }


    /**
     * Get one states by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<StatesDTO> findOne(String id) {
        log.debug("Request to get States : {}", id);
        return statesRepository.findById(id)
            .map(statesMapper::toDto);
    }

    /**
     * Delete the states by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete States : {}", id);
        statesRepository.deleteById(id);
        statesSearchRepository.deleteById(id);
    }

    /**
     * Search for the states corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<StatesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of States for query {}", query);
        return statesSearchRepository.search(queryStringQuery(query), pageable)
            .map(statesMapper::toDto);
    }
}
