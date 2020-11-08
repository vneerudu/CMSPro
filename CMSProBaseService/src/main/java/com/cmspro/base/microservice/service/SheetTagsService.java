package com.cmspro.base.microservice.service;

import com.cmspro.base.microservice.domain.SheetTags;
import com.cmspro.base.microservice.repository.SheetTagsRepository;
import com.cmspro.base.microservice.repository.search.SheetTagsSearchRepository;
import com.cmspro.base.microservice.service.dto.SheetTagsDTO;
import com.cmspro.base.microservice.service.mapper.SheetTagsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link SheetTags}.
 */
@Service
public class SheetTagsService {

    private final Logger log = LoggerFactory.getLogger(SheetTagsService.class);

    private final SheetTagsRepository sheetTagsRepository;

    private final SheetTagsMapper sheetTagsMapper;

    private final SheetTagsSearchRepository sheetTagsSearchRepository;

    public SheetTagsService(SheetTagsRepository sheetTagsRepository, SheetTagsMapper sheetTagsMapper, SheetTagsSearchRepository sheetTagsSearchRepository) {
        this.sheetTagsRepository = sheetTagsRepository;
        this.sheetTagsMapper = sheetTagsMapper;
        this.sheetTagsSearchRepository = sheetTagsSearchRepository;
    }

    /**
     * Save a sheetTags.
     *
     * @param sheetTagsDTO the entity to save.
     * @return the persisted entity.
     */
    public SheetTagsDTO save(SheetTagsDTO sheetTagsDTO) {
        log.debug("Request to save SheetTags : {}", sheetTagsDTO);
        SheetTags sheetTags = sheetTagsMapper.toEntity(sheetTagsDTO);
        sheetTags = sheetTagsRepository.save(sheetTags);
        SheetTagsDTO result = sheetTagsMapper.toDto(sheetTags);
        sheetTagsSearchRepository.save(sheetTags);
        return result;
    }

    /**
     * Get all the sheetTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SheetTagsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SheetTags");
        return sheetTagsRepository.findAll(pageable)
            .map(sheetTagsMapper::toDto);
    }


    /**
     * Get one sheetTags by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SheetTagsDTO> findOne(String id) {
        log.debug("Request to get SheetTags : {}", id);
        return sheetTagsRepository.findById(id)
            .map(sheetTagsMapper::toDto);
    }

    /**
     * Delete the sheetTags by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete SheetTags : {}", id);
        sheetTagsRepository.deleteById(id);
        sheetTagsSearchRepository.deleteById(id);
    }

    /**
     * Search for the sheetTags corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SheetTagsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SheetTags for query {}", query);
        return sheetTagsSearchRepository.search(queryStringQuery(query), pageable)
            .map(sheetTagsMapper::toDto);
    }
}
