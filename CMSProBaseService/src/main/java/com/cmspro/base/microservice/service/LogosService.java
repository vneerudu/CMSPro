package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.Logos;
import com.cmspro.base.microservice.repository.LogosRepository;
import com.cmspro.base.microservice.repository.search.LogosSearchRepository;
import com.cmspro.base.microservice.service.dto.LogosDTO;
import com.cmspro.base.microservice.service.mapper.LogosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Logos}.
 */
@Service
public class LogosService {

    private final Logger log = LoggerFactory.getLogger(LogosService.class);

    private final LogosRepository logosRepository;

    private final LogosMapper logosMapper;

    private final LogosSearchRepository logosSearchRepository;

    public LogosService(LogosRepository logosRepository, LogosMapper logosMapper, LogosSearchRepository logosSearchRepository) {
        this.logosRepository = logosRepository;
        this.logosMapper = logosMapper;
        this.logosSearchRepository = logosSearchRepository;
    }

    /**
     * Save a logos.
     *
     * @param logosDTO the entity to save.
     * @return the persisted entity.
     */
    public LogosDTO save(LogosDTO logosDTO) {
        log.debug("Request to save Logos : {}", logosDTO);
        Logos logos = logosMapper.toEntity(logosDTO);
        logos = logosRepository.save(logos);
        LogosDTO result = logosMapper.toDto(logos);
        logosSearchRepository.save(logos);
        return result;
    }

    /**
     * Get all the logos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<LogosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Logos");
        return logosRepository.findAll(pageable)
            .map(logosMapper::toDto);
    }


    /**
     * Get one logos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<LogosDTO> findOne(String id) {
        log.debug("Request to get Logos : {}", id);
        return logosRepository.findById(id)
            .map(logosMapper::toDto);
    }

    /**
     * Delete the logos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Logos : {}", id);
        logosRepository.deleteById(id);
        logosSearchRepository.deleteById(id);
    }

    /**
     * Search for the logos corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<LogosDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Logos for query {}", query);
        return logosSearchRepository.search(queryStringQuery(query), pageable)
            .map(logosMapper::toDto);
    }
}
