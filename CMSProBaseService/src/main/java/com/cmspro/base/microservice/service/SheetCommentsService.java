package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.SheetComments;
import com.cmspro.base.microservice.repository.SheetCommentsRepository;
import com.cmspro.base.microservice.repository.search.SheetCommentsSearchRepository;
import com.cmspro.base.microservice.service.dto.SheetCommentsDTO;
import com.cmspro.base.microservice.service.mapper.SheetCommentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link SheetComments}.
 */
@Service
public class SheetCommentsService {

    private final Logger log = LoggerFactory.getLogger(SheetCommentsService.class);

    private final SheetCommentsRepository sheetCommentsRepository;

    private final SheetCommentsMapper sheetCommentsMapper;

    private final SheetCommentsSearchRepository sheetCommentsSearchRepository;

    public SheetCommentsService(SheetCommentsRepository sheetCommentsRepository, SheetCommentsMapper sheetCommentsMapper, SheetCommentsSearchRepository sheetCommentsSearchRepository) {
        this.sheetCommentsRepository = sheetCommentsRepository;
        this.sheetCommentsMapper = sheetCommentsMapper;
        this.sheetCommentsSearchRepository = sheetCommentsSearchRepository;
    }

    /**
     * Save a sheetComments.
     *
     * @param sheetCommentsDTO the entity to save.
     * @return the persisted entity.
     */
    public SheetCommentsDTO save(SheetCommentsDTO sheetCommentsDTO) {
        log.debug("Request to save SheetComments : {}", sheetCommentsDTO);
        SheetComments sheetComments = sheetCommentsMapper.toEntity(sheetCommentsDTO);
        sheetComments = sheetCommentsRepository.save(sheetComments);
        SheetCommentsDTO result = sheetCommentsMapper.toDto(sheetComments);
        sheetCommentsSearchRepository.save(sheetComments);
        return result;
    }

    /**
     * Get all the sheetComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SheetCommentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SheetComments");
        return sheetCommentsRepository.findAll(pageable)
            .map(sheetCommentsMapper::toDto);
    }


    /**
     * Get one sheetComments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SheetCommentsDTO> findOne(String id) {
        log.debug("Request to get SheetComments : {}", id);
        return sheetCommentsRepository.findById(id)
            .map(sheetCommentsMapper::toDto);
    }

    /**
     * Delete the sheetComments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete SheetComments : {}", id);
        sheetCommentsRepository.deleteById(id);
        sheetCommentsSearchRepository.deleteById(id);
    }

    /**
     * Search for the sheetComments corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SheetCommentsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SheetComments for query {}", query);
        return sheetCommentsSearchRepository.search(queryStringQuery(query), pageable)
            .map(sheetCommentsMapper::toDto);
    }
}
