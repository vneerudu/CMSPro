package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.Sheets;
import com.cmspro.base.microservice.repository.SheetsRepository;
import com.cmspro.base.microservice.repository.search.SheetsSearchRepository;
import com.cmspro.base.microservice.service.dto.SheetsDTO;
import com.cmspro.base.microservice.service.mapper.SheetsMapper;
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
 * Service Implementation for managing {@link Sheets}.
 */
@Service
public class SheetsService {

    private final Logger log = LoggerFactory.getLogger(SheetsService.class);

    private final SheetsRepository sheetsRepository;

    private final SheetsMapper sheetsMapper;

    private final SheetsSearchRepository sheetsSearchRepository;

    public SheetsService(SheetsRepository sheetsRepository, SheetsMapper sheetsMapper, SheetsSearchRepository sheetsSearchRepository) {
        this.sheetsRepository = sheetsRepository;
        this.sheetsMapper = sheetsMapper;
        this.sheetsSearchRepository = sheetsSearchRepository;
    }

    /**
     * Save a sheets.
     *
     * @param sheetsDTO the entity to save.
     * @return the persisted entity.
     */
    public SheetsDTO save(SheetsDTO sheetsDTO) {
        log.debug("Request to save Sheets : {}", sheetsDTO);
        Sheets sheets = sheetsMapper.toEntity(sheetsDTO);
        sheets = sheetsRepository.save(sheets);
        SheetsDTO result = sheetsMapper.toDto(sheets);
        sheetsSearchRepository.save(sheets);
        return result;
    }

    /**
     * Get all the sheets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SheetsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sheets");
        return sheetsRepository.findAll(pageable)
            .map(sheetsMapper::toDto);
    }



    /**
     *  Get all the sheets where Task is {@code null}.
     *  @return the list of entities.
     */
    public List<SheetsDTO> findAllWhereTaskIsNull() {
        log.debug("Request to get all sheets where Task is null");
        return StreamSupport
            .stream(sheetsRepository.findAll().spliterator(), false)
            .filter(sheets -> sheets.getTask() == null)
            .map(sheetsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sheets by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SheetsDTO> findOne(String id) {
        log.debug("Request to get Sheets : {}", id);
        return sheetsRepository.findById(id)
            .map(sheetsMapper::toDto);
    }

    /**
     * Delete the sheets by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Sheets : {}", id);
        sheetsRepository.deleteById(id);
        sheetsSearchRepository.deleteById(id);
    }

    /**
     * Search for the sheets corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SheetsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sheets for query {}", query);
        return sheetsSearchRepository.search(queryStringQuery(query), pageable)
            .map(sheetsMapper::toDto);
    }
}
