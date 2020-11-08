package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.SheetHistory;
import com.cmspro.base.microservice.repository.SheetHistoryRepository;
import com.cmspro.base.microservice.repository.search.SheetHistorySearchRepository;
import com.cmspro.base.microservice.service.dto.SheetHistoryDTO;
import com.cmspro.base.microservice.service.mapper.SheetHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link SheetHistory}.
 */
@Service
public class SheetHistoryService {

    private final Logger log = LoggerFactory.getLogger(SheetHistoryService.class);

    private final SheetHistoryRepository sheetHistoryRepository;

    private final SheetHistoryMapper sheetHistoryMapper;

    private final SheetHistorySearchRepository sheetHistorySearchRepository;

    public SheetHistoryService(SheetHistoryRepository sheetHistoryRepository, SheetHistoryMapper sheetHistoryMapper, SheetHistorySearchRepository sheetHistorySearchRepository) {
        this.sheetHistoryRepository = sheetHistoryRepository;
        this.sheetHistoryMapper = sheetHistoryMapper;
        this.sheetHistorySearchRepository = sheetHistorySearchRepository;
    }

    /**
     * Save a sheetHistory.
     *
     * @param sheetHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public SheetHistoryDTO save(SheetHistoryDTO sheetHistoryDTO) {
        log.debug("Request to save SheetHistory : {}", sheetHistoryDTO);
        SheetHistory sheetHistory = sheetHistoryMapper.toEntity(sheetHistoryDTO);
        sheetHistory = sheetHistoryRepository.save(sheetHistory);
        SheetHistoryDTO result = sheetHistoryMapper.toDto(sheetHistory);
        sheetHistorySearchRepository.save(sheetHistory);
        return result;
    }

    /**
     * Get all the sheetHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SheetHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SheetHistories");
        return sheetHistoryRepository.findAll(pageable)
            .map(sheetHistoryMapper::toDto);
    }


    /**
     * Get one sheetHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SheetHistoryDTO> findOne(String id) {
        log.debug("Request to get SheetHistory : {}", id);
        return sheetHistoryRepository.findById(id)
            .map(sheetHistoryMapper::toDto);
    }

    /**
     * Delete the sheetHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete SheetHistory : {}", id);
        sheetHistoryRepository.deleteById(id);
        sheetHistorySearchRepository.deleteById(id);
    }

    /**
     * Search for the sheetHistory corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SheetHistoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SheetHistories for query {}", query);
        return sheetHistorySearchRepository.search(queryStringQuery(query), pageable)
            .map(sheetHistoryMapper::toDto);
    }
}
